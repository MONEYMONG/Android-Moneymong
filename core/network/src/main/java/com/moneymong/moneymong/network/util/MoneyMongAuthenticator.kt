package com.moneymong.moneymong.network.util

import com.moneymong.moneymong.domain.repository.token.TokenRepository
import com.moneymong.moneymong.model.sign.RefreshTokenResponse
import com.moneymong.moneymong.network.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class MoneyMongAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val isReIssuePath = response.request.url.toString() == REISSUE_URL

        if (response.code == UNAUTHORIZED && isReIssuePath) {
            handleReIssueFailure()
            return null
        }

        return if (response.code == UNAUTHORIZED) {
            reIssueAndCreateNewRequest(oldRequest = response.request)
        } else {
            null
        }
    }

    private fun handleReIssueFailure() {
        runBlocking {
            tokenRepository.notifyTokenUpdateFailed(true)
            tokenRepository.deleteToken()
        }
    }

    private fun reIssueAndCreateNewRequest(oldRequest: Request): Request? {
        return runBlocking {
            try {
                val refreshToken = tokenRepository.getRefreshToken().getOrThrow()
                val newToken = tokenRepository.getUpdateToken(refreshToken).getOrThrow()
                val newRequest =
                    createNewRequest(oldRequest = oldRequest, accessToken = newToken.accessToken)
                updateTokens(
                    oldRefreshToken = refreshToken,
                    newToken = newToken,
                )

                newRequest
            } catch (e: Exception) {
                handleReIssueFailure()
                null
            }
        }
    }

    private fun createNewRequest(oldRequest: Request, accessToken: String): Request {
        return oldRequest.newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
    }

    private suspend fun updateTokens(oldRefreshToken: String, newToken: RefreshTokenResponse) {
        if (newToken.refreshToken == oldRefreshToken) {
            tokenRepository.updateAccessToken(accessToken = newToken.accessToken)
        } else {
            tokenRepository.updateTokens(
                accessToken = newToken.accessToken,
                refreshToken = newToken.refreshToken,
            )
        }
    }

    companion object {
        private const val REISSUE_URL = "${BuildConfig.MONEYMONG_BASE_URL}api/v1/tokens"
        private const val UNAUTHORIZED = 401
    }
}