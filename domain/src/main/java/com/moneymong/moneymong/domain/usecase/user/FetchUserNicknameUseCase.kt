package com.moneymong.moneymong.domain.usecase.user

import com.moneymong.moneymong.domain.repository.user.UserRepository
import javax.inject.Inject

class FetchUserNicknameUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) {
        suspend operator fun invoke(): String = userRepository.fetchUserNickName()
    }
