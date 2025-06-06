package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import javax.inject.Inject

class SaveAgencyIdUseCase
    @Inject
    constructor(
        private val agencyRepository: AgencyRepository,
    ) {
        suspend operator fun invoke(agencyId: Int) = agencyRepository.saveAgencyId(agencyId)
    }
