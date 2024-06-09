package com.moneymong.moneymong.data.datasource.ledger

import com.moneymong.moneymong.network.api.LedgerApi
import com.moneymong.moneymong.model.ledger.LedgerTransactionRequest
import com.moneymong.moneymong.model.ledger.LedgerTransactionListResponse
import javax.inject.Inject

class LedgerRemoteDataSourceImpl @Inject constructor(
    private val ledgerApi: LedgerApi
): LedgerRemoteDataSource {
    override suspend fun fetchLedgerTransactionList(
        id: Int,
        startYear: Int,
        startMonth: Int,
        endYear: Int,
        endMonth: Int,
        page: Int,
        limit: Int
    ): Result<LedgerTransactionListResponse> =
        ledgerApi.fetchLedgerTransactionList(
            id = id,
            startYear = startYear,
            startMonth = startMonth,
            endYear = endYear,
            endMonth = endMonth,
            page = page,
            limit = limit
        )

    override suspend fun fetchAgencyExistLedger(agencyId: Int): Result<Boolean> =
        ledgerApi.fetchAgencyExistLedger(agencyId = agencyId)

    override suspend fun postLedgerTransaction(id: Int, body: LedgerTransactionRequest) =
        ledgerApi.postLedgerTransaction(id = id, body = body)
}