package com.moneymong.moneymong.data.repository.login

import com.moneymong.moneymong.data.datasource.login.LoginRemoteDataSource
import com.moneymong.moneymong.domain.repository.LoginRepository
import com.moneymong.moneymong.model.sign.LoginType
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val remoteDataSource: LoginRemoteDataSource,
) : LoginRepository {

    override suspend fun postAccessToken(type: LoginType, accessToken: String): Result<Unit> {
        return remoteDataSource.postAccessToken(type = type, accessToken = accessToken)
    }
}