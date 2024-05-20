package com.moneymong.moneymong.domain.usecase.user

import com.moneymong.moneymong.domain.base.BaseUseCase
import com.moneymong.moneymong.domain.repository.user.UserRepository
import com.moneymong.moneymong.model.user.UserResponse
import javax.inject.Inject

class GetMyInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Result<UserResponse>>() {

    override suspend fun invoke(data: Unit): Result<UserResponse> {
        return userRepository.getMyInfo()
    }
}