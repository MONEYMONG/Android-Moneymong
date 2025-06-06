package com.moneymong.moneymong.data.datasource.signup

import com.moneymong.moneymong.model.sign.SearchQueryRequest
import com.moneymong.moneymong.model.sign.UnivRequest
import com.moneymong.moneymong.model.sign.UnivResponse
import com.moneymong.moneymong.model.sign.UniversitiesResponse
import com.moneymong.moneymong.network.api.UniversityApi
import javax.inject.Inject

class UnivRemoteDataSourceImpl
    @Inject
    constructor(private val universityApi: UniversityApi) :
    UnivRemoteDataSource {
        override suspend fun createUniv(body: UnivRequest): Result<Unit> {
            return universityApi.createUniv(body = body)
        }

        override suspend fun searchUniv(searchQuery: SearchQueryRequest): Result<UniversitiesResponse> {
            return universityApi.searchUniv(searchQuery.searchQuery)
        }

        override suspend fun getMyUniv(): Result<UnivResponse> {
            return universityApi.getMyUniv()
        }
    }
