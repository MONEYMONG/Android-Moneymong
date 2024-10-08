package com.moneymong.moneymong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleStartEffect
import com.moneymong.moneymong.common.event.EventTracker
import com.moneymong.moneymong.common.packageinfo.getVersionName
import com.moneymong.moneymong.design_system.theme.MMTheme
import com.moneymong.moneymong.domain.repository.TokenRepository
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
            val currentVersion =
                with(getVersionName(context).split(".").map { element -> element.toInt() }) {
                    KotlinVersion(
                        major = this[0],
                        minor = this[1],
                        patch = this[2]
                    )
                }
            LifecycleStartEffect(key1 = Unit) {
                viewModel.checkShouldUpdate(currentVersion = currentVersion)
                onStopOrDispose {}
            }

            MMTheme {
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