package com.moneymong.moneymong.domain.usecase.agency

import androidx.paging.PagingData
import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.AgencyGetResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAgenciesUseCase @Inject constructor(
    private val agencyRepository: AgencyRepository
) {
    operator fun invoke(): Flow<PagingData<AgencyGetResponse>> {
        return agencyRepository.getAgencies()
    }
}