package com.moneymong.moneymong.domain.usecase.ledgerdetail

import com.moneymong.moneymong.domain.repository.ledgerdetail.LedgerDetailRepository
import com.moneymong.moneymong.model.ledgerdetail.LedgerReceiptRequest
import javax.inject.Inject

class PostLedgerReceiptTransactionUseCase
    @Inject
    constructor(
        private val ledgerDetailRepository: LedgerDetailRepository,
    ) {
        suspend operator fun invoke(
            detailId: Int,
            data: LedgerReceiptRequest,
        ): Result<Unit> = ledgerDetailRepository.postLedgerReceiptTransaction(detailId, data)
    }
