package com.moneymong.moneymong.network.api

import com.moneymong.moneymong.model.sign.UnivRequest
import com.moneymong.moneymong.model.sign.UniversitiesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UniversityApi {

    @POST("api/v1/user-university")
    suspend fun createUniv(
        @Body body: UnivRequest
    ): Result<Unit>

    @GET("api/v1/universities")
    suspend fun searchUniv(
        @Query("keyword") searchQuery: String
    ): Result<UniversitiesResponse>
}