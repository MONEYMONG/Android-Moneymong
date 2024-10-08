package com.moneymong.moneymong.network.api

import com.moneymong.moneymong.model.sign.RefreshTokenRequest
import com.moneymong.moneymong.model.sign.RefreshTokenResponse
import com.moneymong.moneymong.model.sign.TokenRequest
import com.moneymong.moneymong.model.sign.TokenResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface AccessTokenApi {
    @POST("api/v1/users")
    suspend fun postAccessToken(
        @Body body: TokenRequest
    ): Result<TokenResponse>

    @POST("api/v1/tokens")
    suspend fun refreshTokenApi(
        @Body body: RefreshTokenRequest
    ): Result<RefreshTokenResponse>

    @DELETE("api/v1/tokens")
    suspend fun deleteRefreshToken(
        @Body body: RefreshTokenRequest
    )
}