package com.moneymong.moneymong

import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.domain.usecase.version.CheckVersionUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        val checkVersionUpdateUseCase: CheckVersionUpdateUseCase,
    ) : BaseViewModel<MainState, MainSideEffect>(MainState()) {
        fun checkShouldUpdate(version: String) =
            intent {
                checkVersionUpdateUseCase(version = version)
                    .onSuccess { reduce { state.copy(shouldUpdate = false) } }
                    .onFailure { reduce { state.copy(shouldUpdate = it.message?.contains("업데이트") == true) } }
            }
    }
