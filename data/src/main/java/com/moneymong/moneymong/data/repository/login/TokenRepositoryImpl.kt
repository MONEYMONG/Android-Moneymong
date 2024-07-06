package com.moneymong.moneymong.data.repository.login

import com.moneymong.moneymong.data.datasource.login.LoginLocalDataSource
import com.moneymong.moneymong.data.datasource.login.TokenRemoteDataSource
import com.moneymong.moneymong.domain.repository.TokenRepository
import com.moneymong.moneymong.model.sign.RefreshTokenRequest
import com.moneymong.moneymong.model.sign.RefreshTokenResponse
import com.moneymong.moneymong.model.sign.UserDataStoreInfoResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val localDataSource: LoginLocalDataSource,
    private val tokenRemoteDataSource: TokenRemoteDataSource
) : TokenRepository {
    override val tokenUpdateFailed = MutableSharedFlow<Boolean>(replay = 1)

    override suspend fun notifyTokenUpdateFailed(failed: Boolean) {
        tokenUpdateFailed.emit(failed)
    }

    override suspend fun getRefreshToken(): Result<String> {
        return localDataSource.getRefreshToken()
    }

    override suspend fun getAccessToken(): Result<String> {
        return localDataSource.getAccessToken()
    }

    override suspend fun getDataStoreInfo(): Result<UserDataStoreInfoResponse> {
        return localDataSource.getDataStoreInfo()
    }

    override suspend fun getUpdateToken(refreshToken: String): Result<RefreshTokenResponse> {
        return tokenRemoteDataSource.getUpdateToken(refreshToken)
    }

    override suspend fun deleteToken() {
        localDataSource.deleteToken()
    }

    override suspend fun updateTokens(aToken: String, rToken: String) {
        localDataSource.updateTokens(aToken, rToken)
    }

    override suspend fun updateAccessToken(aToken: String) {
        localDataSource.updateAccessToken(aToken)
    }

    override suspend fun deleteRefreshToken(body: RefreshTokenRequest) {
        tokenRemoteDataSource.deleteRefreshToken(body)
    }

    override suspend fun getSchoolInfo(): Result<Boolean> {
        return localDataSource.getSchoolInfo()
    }

    override suspend fun setSchoolInfoExist(infoExist : Boolean) {
        return localDataSource.setSchoolInfoExist(infoExist)
    }

}