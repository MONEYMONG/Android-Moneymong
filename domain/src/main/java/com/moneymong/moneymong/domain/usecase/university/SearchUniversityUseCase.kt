package com.moneymong.moneymong.domain.usecase.university

import com.moneymong.moneymong.domain.repository.univ.UnivRepository
import com.moneymong.moneymong.model.sign.SearchQueryRequest
import com.moneymong.moneymong.model.sign.UniversitiesResponse
import javax.inject.Inject

class SearchUniversityUseCase
    @Inject
    constructor(
        private val univRepository: UnivRepository,
    ) {
        suspend operator fun invoke(searchQuery: String): Result<UniversitiesResponse> {
            return univRepository.searchUniv(SearchQueryRequest(searchQuery))
        }
    }
