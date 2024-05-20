package com.moneymong.moneymong.data.datasource.ledger

import com.moneymong.moneymong.model.ledger.LedgerTransactionRequest
import com.moneymong.moneymong.model.ledger.LedgerTransactionListResponse
import com.moneymong.moneymong.model.ledger.LedgerTransactionResponse

interface LedgerRemoteDataSource {
    suspend fun fetchLedgerTransactionList(id: Int, year: Int, month: Int, page: Int, limit: Int): Result<LedgerTransactionListResponse>
    suspend fun postLedgerTransaction(id: Int, body: LedgerTransactionRequest): Result<LedgerTransactionResponse>
    suspend fun fetchAgencyExistLedger(agencyId: Int): Result<Boolean>
}