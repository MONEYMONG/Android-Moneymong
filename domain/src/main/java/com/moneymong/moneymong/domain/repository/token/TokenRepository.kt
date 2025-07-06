package com.moneymong.moneymong.domain.repository.token

import com.moneymong.moneymong.model.sign.LoginType
import com.moneymong.moneymong.model.sign.RefreshTokenRequest
import com.moneymong.moneymong.model.sign.RefreshTokenResponse
import com.moneymong.moneymong.model.sign.TokenResponse
import com.moneymong.moneymong.model.sign.UserDataStoreInfoResponse
import kotlinx.coroutines.flow.SharedFlow

interface TokenRepository {
    val tokenUpdateFailed: SharedFlow<Boolean>
    suspend fun notifyTokenUpdateFailed(failed: Boolean)
    suspend fun getRefreshToken(): Result<String>
    suspend fun postAccessToken(type: LoginType, accessToken: String): Result<TokenResponse>
    suspend fun getAccessToken(): Result<String>
    suspend fun getDataStoreInfo(): Result<UserDataStoreInfoResponse>
    suspend fun getUpdateToken(refreshToken: String): Result<RefreshTokenResponse>
    suspend fun deleteToken()
    suspend fun updateTokens(accessToken: String, refreshToken: String)
    suspend fun updateAccessToken(accessToken: String)
    suspend fun deleteRefreshToken(body: RefreshTokenRequest)
    suspend fun getSchoolInfo(): Result<Boolean>
    suspend fun setSchoolInfoProvided(infoExist: Boolean)
}