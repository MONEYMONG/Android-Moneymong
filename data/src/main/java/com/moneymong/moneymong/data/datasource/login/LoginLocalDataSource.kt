package com.moneymong.moneymong.data.datasource.login

import com.moneymong.moneymong.model.sign.UserDataStoreInfoResponse

interface LoginLocalDataSource {
    suspend fun getRefreshToken(): Result<String>
    suspend fun getAccessToken(): Result<String>
    suspend fun getDataStoreInfo(): Result<UserDataStoreInfoResponse>
    suspend fun deleteToken()
    suspend fun setDataStore(aToken: String, rToken: String, success: Boolean, infoExist: Boolean)
    suspend fun updateTokens(aToken: String, rToken: String)
    suspend fun updateAccessToken(aToken: String)
    suspend fun getSchoolInfo(): Result<Boolean>
    suspend fun setSchoolInfoProvided(infoExist: Boolean)
}