package com.moneymong.moneymong.data.datasource.signup

import com.moneymong.moneymong.model.sign.SearchQueryRequest
import com.moneymong.moneymong.model.sign.UnivRequest
import com.moneymong.moneymong.model.sign.UnivResponse
import com.moneymong.moneymong.model.sign.UniversitiesResponse

interface UnivRemoteDataSource {
    suspend fun createUniv(body: UnivRequest): Result<Unit>

    suspend fun searchUniv(searchQuery: SearchQueryRequest): Result<UniversitiesResponse>

    suspend fun getMyUniv(): Result<UnivResponse>
}