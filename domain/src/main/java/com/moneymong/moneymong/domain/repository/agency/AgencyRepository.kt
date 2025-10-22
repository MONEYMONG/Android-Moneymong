package com.moneymong.moneymong.domain.repository.agency

import androidx.paging.PagingData
import com.moneymong.moneymong.model.agency.AgencyGetResponse
import com.moneymong.moneymong.model.agency.AgencyJoinRequest
import com.moneymong.moneymong.model.agency.AgencyJoinResponse
import com.moneymong.moneymong.model.agency.AgencyRegisterRequest
import com.moneymong.moneymong.model.agency.CategoryCreateRequest
import com.moneymong.moneymong.model.agency.CategoryCreateResponse
import com.moneymong.moneymong.model.agency.CategoryReadResponse
import com.moneymong.moneymong.model.agency.MyAgencyResponse
import com.moneymong.moneymong.model.agency.RegisterAgencyResponse
import kotlinx.coroutines.flow.Flow

interface AgencyRepository {
    suspend fun registerAgency(request: AgencyRegisterRequest): Result<RegisterAgencyResponse>
    fun getAgencies(): Flow<PagingData<AgencyGetResponse>>
    suspend fun fetchMyAgencyList(): Result<List<MyAgencyResponse>>
    suspend fun fetchAgencyByName(agencyName: String): Result<List<AgencyGetResponse>>
    suspend fun agencyCodeNumbers(data: AgencyJoinRequest): Result<AgencyJoinResponse>

    suspend fun saveAgencyId(agencyId: Int)
    suspend fun fetchAgencyId(): Int
    suspend fun createCategory(request: CategoryCreateRequest): Result<CategoryCreateResponse>
    suspend fun fetchCategories(agencyId: Long): Result<CategoryReadResponse>
}