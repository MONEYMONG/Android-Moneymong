package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import javax.inject.Inject

class FetchAgencyIdUseCase
    @Inject
    constructor(
        private val agencyRepository: AgencyRepository,
    ) {
        suspend operator fun invoke(): Int = agencyRepository.fetchAgencyId()
    }
