package com.moneymong.moneymong.domain.repository.ledger

import com.moneymong.moneymong.model.ledger.LedgerTransactionListResponse
import com.moneymong.moneymong.model.ledger.LedgerTransactionRequest
import com.moneymong.moneymong.model.ledger.LedgerTransactionResponse

interface LedgerRepository {
    suspend fun fetchLedgerTransactionList(id: Int, year: Int, month: Int, page: Int, limit: Int): Result<LedgerTransactionListResponse>
    suspend fun fetchAgencyExistLedger(agencyId: Int): Result<Boolean>
    suspend fun postLedgerTransaction(id: Int, request: LedgerTransactionRequest): Result<LedgerTransactionResponse>
}