package com.moneymong.moneymong.data.datasource.agency

import com.moneymong.moneymong.model.agency.AgenciesGetResponse
import com.moneymong.moneymong.model.agency.AgencyGetResponse
import com.moneymong.moneymong.model.agency.AgencyJoinRequest
import com.moneymong.moneymong.model.agency.AgencyJoinResponse
import com.moneymong.moneymong.model.agency.AgencyRegisterRequest
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import com.moneymong.moneymong.model.agency.RegisterAgencyResponse

interface AgencyRemoteDataSource {
    suspend fun registerAgency(request: AgencyRegisterRequest): Result<RegisterAgencyResponse>
    suspend fun getAgencies(page: Int, size: Int): Result<AgenciesGetResponse>
    suspend fun fetchMyAgencyList(): Result<List<MyAgencyResponse>>
    suspend fun fetchAgencyByName(agencyName: String): Result<List<AgencyGetResponse>>
    suspend fun agencyCodeNumbers(data: AgencyJoinRequest): Result<AgencyJoinResponse>
}