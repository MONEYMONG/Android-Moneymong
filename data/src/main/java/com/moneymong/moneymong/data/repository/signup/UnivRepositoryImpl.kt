package com.moneymong.moneymong.data.repository.signup

import com.moneymong.moneymong.data.datasource.signup.UnivRemoteDataSource
import com.moneymong.moneymong.domain.repository.UnivRepository
import com.moneymong.moneymong.model.sign.SearchQueryRequest
import com.moneymong.moneymong.model.sign.UnivRequest
import com.moneymong.moneymong.model.sign.UnivResponse
import com.moneymong.moneymong.model.sign.UniversitiesResponse
import javax.inject.Inject

class UnivRepositoryImpl @Inject constructor(
    private val univRemoteDataSource: UnivRemoteDataSource
) : UnivRepository {
    override suspend fun createUniv(body: UnivRequest): Result<Unit> {
        return univRemoteDataSource.createUniv(body)
    }

    override suspend fun searchUniv(searchQuery: SearchQueryRequest): Result<UniversitiesResponse> {
        return univRemoteDataSource.searchUniv(searchQuery)
    }

    override suspend fun getMyUniv(): Result<UnivResponse> {
        return univRemoteDataSource.getMyUniv()
    }
}