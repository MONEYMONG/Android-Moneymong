package com.moneymong.moneymong.domain.repository.user

import com.moneymong.moneymong.model.user.UserResponse


interface UserRepository {

    suspend fun getMyInfo(): Result<UserResponse>

    suspend fun withdrawal(): Result<Unit>

    suspend fun logout(): Result<Unit>

    suspend fun saveUserId(userId: Int)

    suspend fun fetchUserId(): Int

    suspend fun saveUserInfo()

    suspend fun saveUserNickName(nickname: String)

    suspend fun fetchUserNickName(): String
    suspend fun saveDeniedCameraPermission(isDenied: Boolean)
    suspend fun fetchDeniedCameraPermission(): Boolean
}