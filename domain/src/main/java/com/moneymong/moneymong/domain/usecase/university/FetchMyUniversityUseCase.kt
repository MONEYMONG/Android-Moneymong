package com.moneymong.moneymong.domain.usecase.university

import com.moneymong.moneymong.domain.repository.UnivRepository
import com.moneymong.moneymong.model.sign.UnivResponse
import javax.inject.Inject

class FetchMyUniversityUseCase @Inject constructor(
    private val univRepository: UnivRepository
) {
    suspend operator fun invoke(): Result<UnivResponse> {
        return univRepository.getMyUniv()
    }
}