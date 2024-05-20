package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.base.BaseUseCase
import com.moneymong.moneymong.domain.repository.AgencyRepository
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import javax.inject.Inject

class FetchMyAgencyListUseCase @Inject constructor(
    private val agencyRepository: AgencyRepository
): BaseUseCase<Unit, Result<List<MyAgencyResponse>>>() {
    override suspend fun invoke(data: Unit): Result<List<MyAgencyResponse>> =
        agencyRepository.fetchMyAgencyList()
}