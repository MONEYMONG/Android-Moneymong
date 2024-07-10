package com.moneymong.moneymong.domain.usecase.login

import com.moneymong.moneymong.domain.repository.LoginRepository
import com.moneymong.moneymong.model.sign.LoginType
import javax.inject.Inject

class PostAccessTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    suspend operator fun invoke(type: LoginType, accessToken: String): Result<Unit> {
        return loginRepository.postAccessToken(type = type, accessToken = accessToken)
    }
}