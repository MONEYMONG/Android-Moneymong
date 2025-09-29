package com.moneymong.moneymong

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleStartEffect
import com.moneymong.moneymong.analytics.AnalyticsTracker
import com.moneymong.moneymong.analytics.LocalAnalyticsTracker
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.theme.MMTheme
import com.moneymong.moneymong.domain.repository.token.TokenRepository
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
                                    data = Uri.parse(playStoreUrl)
                                    setPackage("com.android.vending")
                                }
                                context.startActivity(intent)
                            }
                        )
                    }

                    MoneyMongApp(
                        expired = expired,
                        onChangeExpired = { expired = false }
                    )
                }
            }
        }
    }
}