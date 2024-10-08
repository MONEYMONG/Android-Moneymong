package com.moneymong.moneymong

import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.domain.usecase.version.FetchMinimumUpdateVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val fetchMinimumUpdateVersionUseCase: FetchMinimumUpdateVersionUseCase
) : BaseViewModel<MainState, MainSideEffect>(MainState()) {

    fun checkShouldUpdate(currentVersion: KotlinVersion) = intent {
        fetchMinimumUpdateVersionUseCase().onSuccess {
            val minVersion = it.split(".").map { element -> element.toInt() }
            val shouldUpdate =
                currentVersion.isAtLeast(
                    major = minVersion[0],
                    minor = minVersion[1],
                    patch = minVersion[2]
                ).not()

            reduce {
                state.copy(shouldUpdate = shouldUpdate)
            }
        }
    }
}