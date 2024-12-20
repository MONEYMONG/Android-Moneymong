package com.moneymong.moneymong.data.datasource.user

import com.moneymong.moneymong.model.user.UserResponse

interface UserRemoteDataSource {

    suspend fun getMyInfo(): Result<UserResponse>

    suspend fun withdrawal(): Result<Unit>

    suspend fun logout(refreshToken: String): Result<Unit>
}