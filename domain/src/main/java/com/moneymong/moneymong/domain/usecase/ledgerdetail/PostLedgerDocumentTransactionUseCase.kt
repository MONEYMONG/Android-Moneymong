package com.moneymong.moneymong.domain.usecase.ledgerdetail

import com.moneymong.moneymong.domain.repository.ledgerdetail.LedgerDetailRepository
import com.moneymong.moneymong.model.ledgerdetail.LedgerDocumentRequest
import javax.inject.Inject

class PostLedgerDocumentTransactionUseCase @Inject constructor(
    private val ledgerDetailRepository: LedgerDetailRepository
) {
    suspend operator fun invoke(detailId: Int, data: LedgerDocumentRequest): Result<Unit> =
        ledgerDetailRepository.postLedgerDocumentTransaction(detailId, data)
}