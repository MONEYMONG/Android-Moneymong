package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.AgencyRegisterRequest
import com.moneymong.moneymong.model.agency.RegisterAgencyResponse
import javax.inject.Inject

class RegisterAgencyUseCase
    @Inject
    constructor(
        private val agencyRepository: AgencyRepository,
    ) {
        suspend operator fun invoke(request: AgencyRegisterRequest): Result<RegisterAgencyResponse> {
            return agencyRepository.registerAgency(request = request)
        }
    }
