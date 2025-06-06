package com.moneymong.moneymong.domain.usecase.university

import com.moneymong.moneymong.domain.repository.univ.UnivRepository
import com.moneymong.moneymong.model.sign.UnivRequest
import javax.inject.Inject

class CreateUniversityUseCase
    @Inject
    constructor(
        private val univRepository: UnivRepository,
    ) {
        suspend operator fun invoke(body: UnivRequest): Result<Unit> {
            return univRepository.createUniv(body)
        }
    }
