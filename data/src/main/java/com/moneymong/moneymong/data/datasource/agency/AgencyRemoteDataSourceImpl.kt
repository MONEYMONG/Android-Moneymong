package com.moneymong.moneymong.data.datasource.agency

import com.moneymong.moneymong.model.agency.AgenciesGetResponse
import com.moneymong.moneymong.model.agency.AgencyGetResponse
import com.moneymong.moneymong.model.agency.AgencyJoinRequest
import com.moneymong.moneymong.model.agency.AgencyJoinResponse
import com.moneymong.moneymong.model.agency.AgencyRegisterRequest
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import com.moneymong.moneymong.model.agency.RegisterAgencyResponse
import com.moneymong.moneymong.network.api.AgencyApi
import javax.inject.Inject

class AgencyRemoteDataSourceImpl @Inject constructor(
    private val agencyApi: AgencyApi
) : AgencyRemoteDataSource {

    override suspend fun registerAgency(request: AgencyRegisterRequest): Result<RegisterAgencyResponse> {
        return agencyApi.registerAgency(request = request)
    }

    override suspend fun getAgencies(page: Int, size: Int): Result<AgenciesGetResponse> {
        return agencyApi.getAgencies(page = page, size = size)
    }

    override suspend fun fetchMyAgencyList(): Result<List<MyAgencyResponse>> {
        return agencyApi.fetchMyAgencyList()
    }

    override suspend fun fetchAgencyByName(agencyName: String): Result<List<AgencyGetResponse>> {
        return agencyApi.fetchAgencyByName(name = agencyName)
    }

    override suspend fun agencyCodeNumbers(
        agencyId: Long,
        data: AgencyJoinRequest
    ): Result<AgencyJoinResponse> {
        return agencyApi.agencyCodeNumbers(
            agencyId = agencyId,
            body = data
        )
    }
}