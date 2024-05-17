package com.moneymong.moneymong.domain.usecase.agency


import com.moneymong.moneymong.domain.base.BaseUseCase
import com.moneymong.moneymong.domain.repository.AgencyRepository
import com.moneymong.moneymong.model.agency.AgencyRegisterRequest
import com.moneymong.moneymong.model.agency.RegisterAgencyResponse
import javax.inject.Inject

class RegisterAgencyUseCase @Inject constructor(
    private val agencyRepository: AgencyRepository
) : BaseUseCase<AgencyRegisterRequest, Result<RegisterAgencyResponse>>() {
    override suspend operator fun invoke(data: AgencyRegisterRequest): Result<RegisterAgencyResponse> {
        return agencyRepository.registerAgency(request = data)
    }
}