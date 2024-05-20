package com.moneymong.moneymong.data.datasource.login

import com.moneymong.moneymong.model.sign.RefreshTokenRequest
import com.moneymong.moneymong.model.sign.RefreshTokenResponse

interface TokenRemoteDataSource {
    suspend fun getUpdateToken(refreshToken: String): Result<RefreshTokenResponse>
    suspend fun deleteRefreshToken(body: RefreshTokenRequest)
}