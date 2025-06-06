package com.moneymong.moneymong.data.datasource.login

import com.moneymong.moneymong.model.sign.LoginType
import com.moneymong.moneymong.model.sign.RefreshTokenRequest
import com.moneymong.moneymong.model.sign.RefreshTokenResponse
import com.moneymong.moneymong.model.sign.TokenResponse

interface TokenRemoteDataSource {
    suspend fun postAccessToken(
        type: LoginType,
        accessToken: String,
    ): Result<TokenResponse>

    suspend fun getUpdateToken(refreshToken: String): Result<RefreshTokenResponse>

    suspend fun deleteRefreshToken(body: RefreshTokenRequest)
}
