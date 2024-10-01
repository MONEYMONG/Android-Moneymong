package com.moneymong.moneymong.domain.usecase.ledgerdetail

import com.moneymong.moneymong.domain.repository.ledgerdetail.LedgerDetailRepository
import javax.inject.Inject

class DeleteLedgerDocumentTransactionUseCase @Inject constructor(
    private val ledgerDetailRepository: LedgerDetailRepository
) {
    suspend operator fun invoke(detailId: Int, documentId: Int): Result<Unit> =
        ledgerDetailRepository.deleteLedgerDocumentTransaction(detailId, documentId)
}
