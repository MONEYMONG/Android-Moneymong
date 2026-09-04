package com.moneymong.moneymong

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.LifecycleStartEffect
import com.moneymong.moneymong.analytics.AnalyticsTracker
import com.moneymong.moneymong.analytics.LocalAnalyticsTracker
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.theme.MMTheme
import com.moneymong.moneymong.domain.repository.token.TokenRepository
import com.moneymong.moneymong.invite.InviteDeepLinkParser
import com.moneymong.moneymong.ui.MoneyMongApp
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var tokenRepository: TokenRepository

    @Inject
    lateinit var analyticsTracker: AnalyticsTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        ).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
        }

        setContent {
            val context = LocalContext.current
            val state by viewModel.collectAsState()
            var expired by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                tokenRepository.tokenUpdateFailed.collect { isExpired ->
                    expired = isExpired
                }
            }

            val shouldUpdate = state.shouldUpdate
            LifecycleStartEffect(key1 = Unit, effects = {
                viewModel.checkShouldUpdate(version = BuildConfig.VERSION_NAME)
                onStopOrDispose { }
            })

            CompositionLocalProvider(LocalAnalyticsTracker provides analyticsTracker) {
                MMTheme {
                    if (shouldUpdate) {
                        ErrorDialog(
                            message = "안정적인 머니몽 사용을 위해\n최신 버전으로 업데이트가 필요해요!",
                            confirmText = "업데이트",
                            onConfirm = {
                                val playStoreUrl =
                                    "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = playStoreUrl.toUri()
                                    setPackage("com.android.vending")
                                }
                                context.startActivity(intent)
                            }
                        )
                    }

                    if (state.inviteJoinErrorMessage.isNotEmpty()) {
                        ErrorDialog(
                            message = state.inviteJoinErrorMessage,
                            onConfirm = viewModel::onInviteJoinErrorConfirmed,
                        )
                    }

                    MoneyMongApp(
                        expired = expired,
                        onChangeExpired = { expired = false },
                        inviteCode = state.pendingInvite?.code,
                        inviteJoinFinished = state.inviteJoinFinished,
                        joinAgency = viewModel::joinByInviteCode
                    )
                }
            }
        }

        if (savedInstanceState == null) {
            handleInviteIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleInviteIntent(intent)
    }

    private fun handleInviteIntent(intent: Intent) {
        val code = InviteDeepLinkParser.parse(intent.data) ?: return

        viewModel.onInviteCodeReceived(code)
    }
}
