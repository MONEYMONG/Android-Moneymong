package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.AgencyJoinRequest
import com.moneymong.moneymong.model.agency.AgencyJoinResponse
import javax.inject.Inject

class AgencyJoinUseCase @Inject constructor(
    private val agencyRepository: AgencyRepository
) {
    suspend operator fun invoke(
        data: AgencyJoinRequest
    ): Result<AgencyJoinResponse> {
        return agencyRepository.agencyCodeNumbers(data)
    }
}