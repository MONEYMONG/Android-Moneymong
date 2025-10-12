package com.moneymong.moneymong.data.repository.agency

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moneymong.moneymong.data.datasource.agency.AgencyLocalDataSource
import com.moneymong.moneymong.data.datasource.agency.AgencyRemoteDataSource
import com.moneymong.moneymong.data.pagingsource.AgencyPagingSource
import com.moneymong.moneymong.domain.repository.agency.AgencyRepository
import com.moneymong.moneymong.model.agency.AgencyGetResponse
import com.moneymong.moneymong.model.agency.AgencyJoinRequest
import com.moneymong.moneymong.model.agency.AgencyJoinResponse
import com.moneymong.moneymong.model.agency.AgencyRegisterRequest
import com.moneymong.moneymong.model.agency.CategoryCreateRequest
import com.moneymong.moneymong.model.agency.CategoryCreateResponse
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import com.moneymong.moneymong.model.agency.RegisterAgencyResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AgencyRepositoryImpl @Inject constructor(
    private val agencyRemoteDataSource: AgencyRemoteDataSource,
    private val agencyLocalDataSource: AgencyLocalDataSource
) : AgencyRepository {

    override suspend fun registerAgency(request: AgencyRegisterRequest): Result<RegisterAgencyResponse> {
        return agencyRemoteDataSource.registerAgency(request)
    }

    override fun getAgencies(): Flow<PagingData<AgencyGetResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 10),
            pagingSourceFactory = { AgencyPagingSource(agencyRemoteDataSource) }
        ).flow
    }

    override suspend fun fetchMyAgencyList(): Result<List<MyAgencyResponse>> =
        agencyRemoteDataSource.fetchMyAgencyList()

    override suspend fun fetchAgencyByName(agencyName: String): Result<List<AgencyGetResponse>> =
        agencyRemoteDataSource.fetchAgencyByName(agencyName)

    override suspend fun agencyCodeNumbers(
        data: AgencyJoinRequest
    ): Result<AgencyJoinResponse> {
        return agencyRemoteDataSource.agencyCodeNumbers(data)
    }

    override suspend fun saveAgencyId(agencyId: Int) =
        agencyLocalDataSource.saveAgencyId(agencyId = agencyId)

    override suspend fun fetchAgencyId(): Int =
        agencyLocalDataSource.fetchAgencyId()

    override suspend fun createCategory(request: CategoryCreateRequest): Result<CategoryCreateResponse> =
        agencyRemoteDataSource.createCategory(request = request)
}