package com.moneymong.moneymong.domain.usecase.ledger

import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import com.moneymong.moneymong.model.ledger.LedgerTransactionListResponse
import javax.inject.Inject

class FetchLedgerTransactionListUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {
    suspend operator fun invoke(id: Int, year: Int, month: Int, page: Int, limit: Int): Result<LedgerTransactionListResponse> =
        ledgerRepository.fetchLedgerTransactionList(id, year, month, page, limit)
}