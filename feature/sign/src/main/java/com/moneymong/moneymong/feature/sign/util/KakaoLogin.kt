package com.moneymong.moneymong.feature.sign.util

import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

internal object KakaoLogin {
    operator fun invoke(
        context: Context,
        onSuccess: (accessToken: String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(
                context = context,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        } else {
            loginWithKakaoAccount(
                context = context,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }

    private fun loginWithKakaoTalk(
        context: Context,
        onSuccess: (accessToken: String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        UserApiClient.instance.loginWithKakaoTalk(context) inner@{ token, error ->
            if (error != null) {
                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    onFailure(error)
                    return@inner
                }
                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                loginWithKakaoAccount(
                    context = context,
                    onSuccess = onSuccess,
                    onFailure = onFailure
                )
            } else if (token != null) {
                onSuccess(token.accessToken)
            }
        }
    }

    private fun loginWithKakaoAccount(
        context: Context,
        onSuccess: (accessToken: String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                onFailure(error)
            } else if (token != null) {
                onSuccess(token.accessToken)
            }
        }
    }
}