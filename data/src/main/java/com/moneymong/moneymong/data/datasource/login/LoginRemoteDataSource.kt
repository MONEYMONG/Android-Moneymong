package com.moneymong.moneymong.data.datasource.login

import com.moneymong.moneymong.model.sign.LoginType


interface LoginRemoteDataSource {
    suspend fun postAccessToken(type: LoginType, accessToken: String): Result<Unit>
}
