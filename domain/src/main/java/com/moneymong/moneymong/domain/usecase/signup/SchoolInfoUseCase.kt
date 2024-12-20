package com.moneymong.moneymong.domain.usecase.signup

import com.moneymong.moneymong.domain.repository.token.TokenRepository
import javax.inject.Inject

class SchoolInfoUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(infoExist: Boolean){
        tokenRepository.setSchoolInfoProvided(infoExist)
    }
}