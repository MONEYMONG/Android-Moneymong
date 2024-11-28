package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.AgencyGetResponse
import javax.inject.Inject

class FetchAgencyByNameUseCase @Inject constructor(
    private val agencyRepository: AgencyRepository,
) {

    suspend operator fun invoke(agencyName: String): Result<List<AgencyGetResponse>> {
        return agencyRepository.fetchAgencyByName(agencyName = agencyName)
    }
}