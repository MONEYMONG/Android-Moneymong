package com.moneymong.moneymong.data.repository.user

import com.moneymong.moneymong.data.datasource.login.LoginLocalDataSource
import com.moneymong.moneymong.data.datasource.user.UserLocalDataSource
import com.moneymong.moneymong.data.datasource.user.UserRemoteDataSource
import com.moneymong.moneymong.domain.repository.user.UserRepository
import com.moneymong.moneymong.model.user.UserResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val loginLocalDataSource: LoginLocalDataSource
) : UserRepository {

    override suspend fun getMyInfo(): Result<UserResponse> {
        return userRemoteDataSource.getMyInfo()
    }

    override suspend fun withdrawal(): Result<Unit> {
        return userRemoteDataSource.withdrawal()
            .onSuccess {
                loginLocalDataSource.deleteToken()
            }
    }

    override suspend fun logout(): Result<Unit> {
        val refreshToken = loginLocalDataSource.getRefreshToken().getOrElse {
            return Result.failure(it)
        }
        return userRemoteDataSource.logout(refreshToken)
            .onSuccess {
                loginLocalDataSource.deleteToken()
            }
    }

    override suspend fun saveUserId(userId: Int) =
        userLocalDataSource.saveUserId(userId = userId)

    override suspend fun fetchUserId(): Int =
        userLocalDataSource.fetchUserId()

    override suspend fun saveUserInfo() {
        userRemoteDataSource.getMyInfo()
            .onSuccess {
                userLocalDataSource.saveUserId(it.id.toInt())
                userLocalDataSource.saveUserNickName(it.name)
            }.onFailure {
                userLocalDataSource.saveUserId(0)
                userLocalDataSource.saveUserNickName("에러가 발생했습니다.")
            }
    }


    override suspend fun saveUserNickName(nickname: String) =
        userLocalDataSource.saveUserNickName(nickname = nickname)

    override suspend fun fetchUserNickName(): String =
        userLocalDataSource.fetchUserNickName()

    override suspend fun saveDeniedCameraPermission(isDenied: Boolean) =
        userLocalDataSource.saveDeniedCameraPermission(isDenied = isDenied)

    override suspend fun fetchDeniedCameraPermission(): Boolean =
        userLocalDataSource.fetchDeniedCameraPermission()
}
