package com.moneymong.moneymong.feature.sign.viewmodel

import androidx.lifecycle.viewModelScope
import com.moneymong.moneymong.android.BaseViewModel
import com.moneymong.moneymong.domain.usecase.agency.FetchMyAgencyListUseCase
import com.moneymong.moneymong.domain.usecase.token.PostAccessTokenUseCase
import com.moneymong.moneymong.feature.sign.sideeffect.LoginSideEffect
import com.moneymong.moneymong.feature.sign.state.LoginState
import com.moneymong.moneymong.model.sign.LoginType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postAccessTokenUseCase: PostAccessTokenUseCase,
    private val fetchMyAgencyListUseCase: FetchMyAgencyListUseCase,
) : BaseViewModel<LoginState, LoginSideEffect>(LoginState()) {

    fun onKakaoLoginSuccess(accessToken: String) = intent {
        postAccessTokenUseCase(type = LoginType.KAKAO, accessToken = accessToken).onSuccess {
            checkAgencyExists()
        }.onFailure {
            visibleErrorChanged(isVisibleError = true)
            reduce {
                state.copy(
                    visibleError = true,
                    errorMessage = "문제가 발생했습니다.\n다시 시도해주세요"
                )
            }
        }
    }

    fun onKakaoLoginFailure(throwable: Throwable) = visibleErrorChanged(isVisibleError = true)

    private fun checkAgencyExists() = viewModelScope.launch {
        fetchMyAgencyListUseCase()
            .onSuccess {
                val hasAnyAgency = it.isNotEmpty()
                intent { reduce { state.copy(hasAnyAgency = hasAnyAgency) } }
            }.onFailure {
                visibleErrorChanged(true)
            }
    }

    fun visibleErrorChanged(isVisibleError: Boolean) = intent {
        reduce {
            state.copy(
                visibleError = isVisibleError,
                errorMessage = "문제가 발생했습니다.\n다시 시도해주세요",
            )
        }
    }
}