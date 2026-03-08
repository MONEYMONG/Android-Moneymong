package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.CategoryCreateRequest
import com.moneymong.moneymong.model.agency.CategoryCreateResponse
import javax.inject.Inject

class CreateCategoryUseCase @Inject constructor(
    private val agencyRepository: AgencyRepository,
) {
    suspend operator fun invoke(request: CategoryCreateRequest): Result<CategoryCreateResponse> =
        agencyRepository.createCategory(request = request)
}