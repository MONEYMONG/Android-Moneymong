package com.moneymong.moneymong

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleStartEffect
import com.moneymong.moneymong.common.event.EventTracker
import com.moneymong.moneymong.design_system.error.ErrorDialog
import com.moneymong.moneymong.design_system.theme.MMTheme
import com.moneymong.moneymong.domain.repository.token.TokenRepository
import com.moneymong.moneymong.ui.MoneyMongApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var tokenRepository: TokenRepository
    private val expired = mutableStateOf(false)

    @Inject
    lateinit var eventTracker: EventTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventTracker.initialize()

        setContent {
            val context = LocalContext.current
            val state by viewModel.collectAsState()

            val shouldUpdate = state.shouldUpdate
            LifecycleStartEffect(key1 = Unit, effects = {
                viewModel.checkShouldUpdate(version = BuildConfig.VERSION_NAME)
                onStopOrDispose { }
            })

            MMTheme {
                if (shouldUpdate) {
                    ErrorDialog(
                        message = "안정적인 머니몽 사용을 위해\n최신 버전으로 업데이트가 필요해요!",
                        confirmText = "업데이트",
                        onConfirm = {
                            val playStoreUrl =
                                "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                            val intent =
                                Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse(playStoreUrl)
                                    setPackage("com.android.vending")
                                }
                            context.startActivity(intent)
                        },
                    )
                }

                MoneyMongApp(expired.value) {
                    expired.value = false
                }
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            tokenRepository.tokenUpdateFailed.collect {
                expired.value = it
            }
        }
    }
}
