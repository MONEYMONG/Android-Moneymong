package com.moneymong.moneymong.domain.repository

import com.moneymong.moneymong.model.sign.LoginType

interface LoginRepository {
    suspend fun postAccessToken(type: LoginType, accessToken: String): Result<Unit>
}