package com.moneymong.moneymong.domain.usecase.user

import com.moneymong.moneymong.domain.repository.user.UserRepository
import com.moneymong.moneymong.model.user.UserResponse
import javax.inject.Inject

class FetchMyInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<UserResponse> {
        return userRepository.getMyInfo()
    }
}