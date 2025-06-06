package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import javax.inject.Inject

class FetchMyAgencyListUseCase
    @Inject
    constructor(
        private val agencyRepository: AgencyRepository,
    ) {
        suspend operator fun invoke(): Result<List<MyAgencyResponse>> = agencyRepository.fetchMyAgencyList()
    }
