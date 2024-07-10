package com.moneymong.moneymong.data.datasource.login

import android.util.Log
import com.moneymong.moneymong.data.datasource.user.UserLocalDataSource
import com.moneymong.moneymong.model.sign.LoginType
import com.moneymong.moneymong.model.sign.TokenRequest
import com.moneymong.moneymong.network.api.AccessTokenApi
import com.moneymong.moneymong.network.api.UserApi
import javax.inject.Inject


class LoginRemoteDataSourceImpl @Inject constructor(
    private val accessTokenApi: AccessTokenApi,
    private val userApi: UserApi,
    private val loginLocalDataSourceImpl: LoginLocalDataSourceImpl,
    private val userLocalDataSource: UserLocalDataSource,
) : LoginRemoteDataSource {

    override suspend fun postAccessToken(type: LoginType, accessToken: String): Result<Unit> {
        return accessTokenApi.postAccessToken(TokenRequest(type.name, accessToken)).fold(
            onSuccess = {
                Log.d(
                    "success", "${it.accessToken}\n" +
                            "${it.refreshToken}\n" +
                            "${it.loginSuccess}\n" +
                            "${it.schoolInfoExist}"
                )
                loginLocalDataSourceImpl.setDataStore(
                    it.accessToken,
                    it.refreshToken,
                    it.loginSuccess,
                    it.schoolInfoExist
                )
                saveUserInfo()
                Result.success(Unit)
            },
            onFailure = {
                Log.d("failure", it.message.toString())
                Result.failure(it)
            }
        )
    }


    private suspend fun saveUserInfo() {
        userApi.getMyInfo()
            .onSuccess {
                userLocalDataSource.saveUserId(it.id.toInt())
                userLocalDataSource.saveUserNickName(it.name)
            }.onFailure {
                userLocalDataSource.saveUserId(0)
                userLocalDataSource.saveUserNickName("에러가 발생했습니다.")
            }
    }
}