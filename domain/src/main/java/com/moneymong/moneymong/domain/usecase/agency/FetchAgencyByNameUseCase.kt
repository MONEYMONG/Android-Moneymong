package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.AgencyGetResponse
import javax.inject.Inject

class FetchAgencyByNameUseCase @Inject constructor(
    private val agencyRepository: AgencyRepository,
) {

    suspend operator fun invoke(agencyName: String): Result<List<AgencyGetResponse>> {
//        return agencyRepository.fetchAgencyBySearch()
        return Result.success(
            listOf(
                AgencyGetResponse(
                    id = 1,
                    name = "Agency 1",
                    headCount = 10,
                    type = "STUDENT_COUNCIL"
                ),
                AgencyGetResponse(
                    id = 2,
                    name = "Agency 2",
                    headCount = 20,
                    type = "IN_SCHOOL_CLUB"
                ),
                AgencyGetResponse(
                    id = 3,
                    name = "Agency 3",
                    headCount = 30,
                    type = "STUDENT_COUNCIL"
                ),
                AgencyGetResponse(
                    id = 4,
                    name = "Agency 4",
                    headCount = 40,
                    type = "IN_SCHOOL_CLUB"
                ),
            )
        )
    }
}