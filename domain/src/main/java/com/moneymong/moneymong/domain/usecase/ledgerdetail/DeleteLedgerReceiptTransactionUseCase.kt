package com.moneymong.moneymong.domain.usecase.ledgerdetail

import com.moneymong.moneymong.domain.repository.ledgerdetail.LedgerDetailRepository
import javax.inject.Inject

class DeleteLedgerReceiptTransactionUseCase
    @Inject
    constructor(
        private val ledgerDetailRepository: LedgerDetailRepository,
    ) {
        suspend operator fun invoke(
            detailId: Int,
            receiptId: Int,
        ): Result<Unit> = ledgerDetailRepository.deleteLedgerReceiptTransaction(detailId, receiptId)
    }
