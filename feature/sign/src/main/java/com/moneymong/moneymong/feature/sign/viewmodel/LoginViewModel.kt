package com.moneymong.moneymong.feature.sign.viewmodel

import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.moneymong.moneymong.common.base.BaseViewModel
import com.moneymong.moneymong.domain.usecase.token.PostAccessTokenUseCase
import com.moneymong.moneymong.domain.usecase.token.TokenUseCase
import com.moneymong.moneymong.feature.sign.sideeffect.LoginSideEffect
import com.moneymong.moneymong.feature.sign.state.LoginState
import com.moneymong.moneymong.model.sign.LoginType
import com.moneymong.moneymong.network.util.TokenCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenUseCase: TokenUseCase,
    private val postAccessTokenUseCase: PostAccessTokenUseCase
) : BaseViewModel<LoginState, LoginSideEffect>(LoginState()), TokenCallback {

    fun onKakaoLoginSuccess(accessToken: String) = intent {
        postAccessTokenUseCase(type = LoginType.KAKAO, accessToken = accessToken).onSuccess {
            getSchoolInfo()
        }.onFailure {
            Log.d("LoginViewModel, in Success.Failure", it.message ?: it.toString())
            reduce {
                state.copy(
                    visibleError = true,
                    errorMessage = "문제가 발생했습니다.\n다시 시도해주세요"
                )
            }
        }
    }

    fun onKakaoLoginFailure(throwable: Throwable) = intent {
        Log.d("LoginViewModel, in Failure", KakaoSdk.keyHash)
        reduce {
            state.copy(
                visibleError = true,
                errorMessage = "문제가 발생했습니다.\n다시 시도해주세요"
            )
        }
    }


    private fun getSchoolInfo() = intent {
        tokenUseCase.getSchoolInfo().onSuccess { result ->
            if (result) {
                reduce {
                    state.copy(
                        isSchoolInfoProvided = true
                    )
                }
            } else {
                reduce {
                    state.copy(
                        isSchoolInfoProvided = false
                    )
                }
            }
        }.onFailure {
            reduce {
                state.copy(
                    visibleError = true,
                    errorMessage = "문제가 발생했습니다.\n다시 시도해주세요"
                )
            }
        }
    }


    override suspend fun onTokenFailure() {
        intent {
            reduce {
                state.copy(
                    isLoginRequired = true
                )
            }
        }
    }

    fun isLoginRequiredChanged(boolean: Boolean) = intent {
        reduce {
            state.copy(
                isLoginRequired = boolean
            )
        }
    }

    fun visibleErrorChanged(isVisibleError: Boolean) = intent {
        reduce {
            state.copy(
                visibleError = isVisibleError,
            )
        }
    }

    fun isSchoolInfoProvidedChanged(isSchoolInfoProvided: Boolean?) = intent {
        reduce {
            state.copy(
                isSchoolInfoProvided = isSchoolInfoProvided
            )
        }
    }
}