package com.moneymong.moneymong.domain.usecase.token

import com.moneymong.moneymong.domain.repository.token.TokenRepository
import com.moneymong.moneymong.domain.repository.user.UserRepository
import com.moneymong.moneymong.model.sign.LoginType
import com.moneymong.moneymong.model.sign.TokenResponse
import javax.inject.Inject

class PostAccessTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(type: LoginType, accessToken: String): Result<TokenResponse> {
        return tokenRepository.postAccessToken(type = type, accessToken = accessToken).onSuccess {
            userRepository.saveUserInfo()
        }
    }
}