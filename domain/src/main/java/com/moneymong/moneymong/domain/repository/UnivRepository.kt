package com.moneymong.moneymong.domain.repository

import com.moneymong.moneymong.model.sign.SearchQueryRequest
import com.moneymong.moneymong.model.sign.UnivRequest
import com.moneymong.moneymong.model.sign.UniversitiesResponse

interface UnivRepository {
    suspend fun createUniv(body: UnivRequest) : Result<Unit>

    suspend fun searchUniv(searchQuery: SearchQueryRequest) : Result<UniversitiesResponse>
}