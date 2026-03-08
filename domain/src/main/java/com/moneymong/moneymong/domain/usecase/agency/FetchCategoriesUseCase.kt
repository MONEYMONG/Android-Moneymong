package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.CategoryReadResponse
import javax.inject.Inject

class FetchCategoriesUseCase @Inject constructor(
    private val agencyRepository: AgencyRepository
) {
    suspend operator fun invoke(agencyId: Long): Result<CategoryReadResponse> =
        agencyRepository.fetchCategories(agencyId = agencyId)
}