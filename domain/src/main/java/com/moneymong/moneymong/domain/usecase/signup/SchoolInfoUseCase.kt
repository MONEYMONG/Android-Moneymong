package com.moneymong.moneymong.domain.usecase.signup

import com.moneymong.moneymong.domain.repository.TokenRepository
import javax.inject.Inject

class SchoolInfoUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(infoExist: Boolean){
        tokenRepository.setSchoolInfoExist(infoExist)
    }
}