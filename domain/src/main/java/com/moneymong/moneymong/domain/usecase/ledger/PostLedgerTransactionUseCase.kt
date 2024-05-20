package com.moneymong.moneymong.domain.usecase.ledger

import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import com.moneymong.moneymong.model.ledger.LedgerTransactionRequest
import com.moneymong.moneymong.model.ledger.LedgerTransactionResponse
import javax.inject.Inject

class PostLedgerTransactionUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {
    suspend operator fun invoke(id: Int, request: LedgerTransactionRequest): Result<LedgerTransactionResponse> =
        ledgerRepository.postLedgerTransaction(id, request)
}