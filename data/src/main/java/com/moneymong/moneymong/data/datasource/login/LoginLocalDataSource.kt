package com.moneymong.moneymong.data.datasource.login

import com.moneymong.moneymong.model.sign.UserDataStoreInfoResponse

interface LoginLocalDataSource {
    suspend fun getRefreshToken(): Result<String>
    suspend fun getAccessToken(): Result<String>
    suspend fun getDataStoreInfo(): Result<UserDataStoreInfoResponse>
    suspend fun deleteToken()
    suspend fun setDataStore(accessToken: String, refreshToken: String, success: Boolean, infoExist: Boolean)
    suspend fun updateTokens(accessToken: String, refreshToken: String)
    suspend fun updateAccessToken(accessToken: String)
    suspend fun getSchoolInfo(): Result<Boolean>
    suspend fun setSchoolInfoProvided(infoExist: Boolean)
}