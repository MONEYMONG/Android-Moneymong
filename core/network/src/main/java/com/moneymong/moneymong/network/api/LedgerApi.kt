package com.moneymong.moneymong.network.api

import com.moneymong.moneymong.model.ledger.LedgerTransactionRequest
import com.moneymong.moneymong.model.ledger.LedgerTransactionListResponse
import com.moneymong.moneymong.model.ledger.LedgerTransactionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LedgerApi {

    // GET
    @GET("api/v2/ledger/{id}")
    suspend fun fetchLedgerTransactionList(
        @Path("id") id: Int,
        @Query("startYear") startYear: Int,
        @Query("startMonth") startMonth: Int,
        @Query("endYear") endYear: Int,
        @Query("endMonth") endMonth: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Result<LedgerTransactionListResponse>

    @GET("api/v1/ledger/agencies/{agencyId}/exist")
    suspend fun fetchAgencyExistLedger(
        @Path("agencyId") agencyId: Int
    ): Result<Boolean>

    // POST
    @POST("api/v1/ledger/{id}")
    suspend fun postLedgerTransaction(
        @Path("id") id: Int,
        @Body body: LedgerTransactionRequest
    ): Result<LedgerTransactionResponse>
}