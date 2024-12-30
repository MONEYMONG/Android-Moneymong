package com.moneymong.moneymong.data.repository.token

import com.moneymong.moneymong.data.datasource.login.LoginLocalDataSource
import com.moneymong.moneymong.data.datasource.login.TokenRemoteDataSource
import com.moneymong.moneymong.domain.repository.token.TokenRepository
import com.moneymong.moneymong.model.sign.LoginType
import com.moneymong.moneymong.model.sign.RefreshTokenRequest
import com.moneymong.moneymong.model.sign.RefreshTokenResponse
import com.moneymong.moneymong.model.sign.TokenResponse
import com.moneymong.moneymong.model.sign.UserDataStoreInfoResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val loginLocalDataSource: LoginLocalDataSource,
    private val tokenRemoteDataSource: TokenRemoteDataSource,
) : TokenRepository {
    override val tokenUpdateFailed = MutableSharedFlow<Boolean>(replay = 1)

    override suspend fun notifyTokenUpdateFailed(failed: Boolean) {
        tokenUpdateFailed.emit(failed)
    }

    override suspend fun getRefreshToken(): Result<String> {
        return loginLocalDataSource.getRefreshToken()
    }

    override suspend fun postAccessToken(
        type: LoginType,
        accessToken: String
    ): Result<TokenResponse> {
        return tokenRemoteDataSource.postAccessToken(type = type, accessToken = accessToken)
            .onSuccess {
                loginLocalDataSource.setDataStore(
                    it.accessToken,
                    it.refreshToken,
                    it.loginSuccess,
                    it.schoolInfoProvided
                )
            }
    }

    override suspend fun getAccessToken(): Result<String> {
        return loginLocalDataSource.getAccessToken()
    }

    override suspend fun getDataStoreInfo(): Result<UserDataStoreInfoResponse> {
        return loginLocalDataSource.getDataStoreInfo()
    }

    override suspend fun getUpdateToken(refreshToken: String): Result<RefreshTokenResponse> {
        return tokenRemoteDataSource.getUpdateToken(refreshToken)
    }

    override suspend fun deleteToken() {
        loginLocalDataSource.deleteToken()
    }

    override suspend fun updateTokens(aToken: String, rToken: String) {
        loginLocalDataSource.updateTokens(aToken, rToken)
    }

    override suspend fun updateAccessToken(aToken: String) {
        loginLocalDataSource.updateAccessToken(aToken)
    }

    override suspend fun deleteRefreshToken(body: RefreshTokenRequest) {
        tokenRemoteDataSource.deleteRefreshToken(body)
    }

    override suspend fun getSchoolInfo(): Result<Boolean> {
        return loginLocalDataSource.getSchoolInfo()
    }

    override suspend fun setSchoolInfoProvided(infoExist: Boolean) {
        return loginLocalDataSource.setSchoolInfoProvided(infoExist)
    }

}