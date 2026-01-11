package com.moneymong.moneymong.domain.usecase.agency

import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.CategoryDeleteRequest
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val agencyRepository: AgencyRepository,
){
    suspend operator fun invoke(request: CategoryDeleteRequest): Result<Unit> =
        agencyRepository.deleteCategory(request = request)
}