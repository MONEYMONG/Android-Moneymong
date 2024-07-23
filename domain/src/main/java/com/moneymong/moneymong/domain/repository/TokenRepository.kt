package com.moneymong.moneymong.domain.repository

import com.moneymong.moneymong.model.sign.LoginType
import com.moneymong.moneymong.model.sign.RefreshTokenRequest
import com.moneymong.moneymong.model.sign.RefreshTokenResponse
import com.moneymong.moneymong.model.sign.TokenResponse
import com.moneymong.moneymong.model.sign.UserDataStoreInfoResponse
import kotlinx.coroutines.flow.MutableSharedFlow

interface TokenRepository {
    val tokenUpdateFailed: MutableSharedFlow<Boolean>
    suspend fun notifyTokenUpdateFailed(failed: Boolean)
    suspend fun getRefreshToken(): Result<String>
    suspend fun postAccessToken(type: LoginType, accessToken: String): Result<TokenResponse>
    suspend fun getAccessToken(): Result<String>
    suspend fun getDataStoreInfo(): Result<UserDataStoreInfoResponse>
    suspend fun getUpdateToken(refreshToken: String): Result<RefreshTokenResponse>
    suspend fun deleteToken()
    suspend fun updateTokens(aToken: String, rToken: String)
    suspend fun updateAccessToken(aToken: String)
    suspend fun deleteRefreshToken(body: RefreshTokenRequest)
    suspend fun getSchoolInfo(): Result<Boolean>
    suspend fun setSchoolInfoExist(infoExist: Boolean)
}