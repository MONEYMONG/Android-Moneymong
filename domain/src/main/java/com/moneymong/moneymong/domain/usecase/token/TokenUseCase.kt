package com.moneymong.moneymong.domain.usecase.token

import com.moneymong.moneymong.domain.repository.token.TokenRepository
import com.moneymong.moneymong.model.sign.UserDataStoreInfoResponse
import javax.inject.Inject

class TokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend fun getAccessToken(): Result<String> {
        return tokenRepository.getAccessToken()
    }

    suspend fun getDataStoreInfo(): Result<UserDataStoreInfoResponse> {
        return tokenRepository.getDataStoreInfo()
    }

    suspend fun getSchoolInfo(): Result<Boolean> {
        return tokenRepository.getSchoolInfo()
    }
}