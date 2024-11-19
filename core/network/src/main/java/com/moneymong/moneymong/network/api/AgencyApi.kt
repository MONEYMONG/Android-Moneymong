package com.moneymong.moneymong.network.api

import com.moneymong.moneymong.model.agency.AgenciesGetResponse
import com.moneymong.moneymong.model.agency.AgencyGetResponse
import com.moneymong.moneymong.model.agency.AgencyJoinRequest
import com.moneymong.moneymong.model.agency.AgencyJoinResponse
import com.moneymong.moneymong.model.agency.AgencyRegisterRequest
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import com.moneymong.moneymong.model.agency.RegisterAgencyResponse
import com.moneymong.moneymong.model.member.InvitationCodeResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AgencyApi {

    // GET
    @GET("api/v1/agencies/{agencyId}/invitation-code")
    suspend fun getInvitationCode(
        @Path("agencyId") agencyId: Long
    ): Result<InvitationCodeResponse>

    @GET("api/v1/agencies")
    suspend fun getAgencies(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Result<AgenciesGetResponse>

    @GET("api/v1/agencies/me")
    suspend fun fetchMyAgencyList(): Result<List<MyAgencyResponse>>

    @GET("api/v1/agencies/search")
    suspend fun fetchAgencyByName(
        @Query("keyword") name: String
    ): Result<List<AgencyGetResponse>>

    // POST
    @POST("/api/v1/agencies/{agencyId}/invitation-code")
    suspend fun agencyCodeNumbers(
        @Path("agencyId") agencyId: Long,
        @Body body: AgencyJoinRequest
    ): Result<AgencyJoinResponse>

    @POST("api/v1/agencies")
    suspend fun registerAgency(
        @Body request: AgencyRegisterRequest
    ): Result<RegisterAgencyResponse>

    // PATCH
    @PATCH("api/v1/agencies/{agencyId}/invitation-code")
    suspend fun reInvitationCode(
        @Path("agencyId") agencyId: Long
    ): Result<InvitationCodeResponse>

    //DELETE
    @DELETE("api/v1/agencies/{agencyId}")
    suspend fun deleteAgency(
        @Path("agencyId") agencyId: Int
    ): Result<Unit>
}