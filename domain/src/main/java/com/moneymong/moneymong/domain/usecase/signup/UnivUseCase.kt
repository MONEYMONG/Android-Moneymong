package com.moneymong.moneymong.domain.usecase.signup

import com.moneymong.moneymong.domain.repository.UnivRepository
import com.moneymong.moneymong.model.sign.SearchQueryRequest
import com.moneymong.moneymong.model.sign.UnivRequest
import com.moneymong.moneymong.model.sign.UniversitiesResponse
import javax.inject.Inject

class UnivUseCase @Inject constructor(
    private val univRepository: UnivRepository
){
    suspend fun createUniv(body : UnivRequest) : Result<Unit>{
        return univRepository.createUniv(body)
    }

    suspend fun searchUniv(searchQuery : String) : Result<UniversitiesResponse> {
        return univRepository.searchUniv(SearchQueryRequest(searchQuery))
    }
}