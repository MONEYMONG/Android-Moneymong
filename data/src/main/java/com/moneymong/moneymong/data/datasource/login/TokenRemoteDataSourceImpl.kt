package com.moneymong.moneymong.data.datasource.login

import com.moneymong.moneymong.model.sign.LoginType
import com.moneymong.moneymong.model.sign.RefreshTokenRequest
import com.moneymong.moneymong.model.sign.RefreshTokenResponse
import com.moneymong.moneymong.model.sign.TokenRequest
import com.moneymong.moneymong.model.sign.TokenResponse
import com.moneymong.moneymong.network.api.AccessTokenApi
import javax.inject.Inject
import javax.inject.Provider

class TokenRemoteDataSourceImpl @Inject constructor(
    private val accessTokenApiProvider: Provider<AccessTokenApi>
) : TokenRemoteDataSource {
    private val accessTokenApi by lazy { accessTokenApiProvider.get() }

    override suspend fun postAccessToken(
        type: LoginType,
        accessToken: String
    ): Result<TokenResponse> {
        return accessTokenApi.postAccessToken(TokenRequest(type.name, accessToken, "", ""))
    }

    override suspend fun getUpdateToken(refreshToken: String): Result<RefreshTokenResponse> {
        return accessTokenApi.refreshTokenApi(RefreshTokenRequest(refreshToken))
    }

    override suspend fun deleteRefreshToken(body: RefreshTokenRequest) {
        return accessTokenApi.deleteRefreshToken(body)
    }
}