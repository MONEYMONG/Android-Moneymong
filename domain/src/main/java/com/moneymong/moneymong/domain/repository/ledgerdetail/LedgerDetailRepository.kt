package com.moneymong.moneymong.domain.repository.ledgerdetail

import com.moneymong.moneymong.model.ledgerdetail.LedgerDocumentRequest
import com.moneymong.moneymong.model.ledgerdetail.LedgerReceiptRequest
import com.moneymong.moneymong.model.ledgerdetail.LedgerTransactionDetailRequest
import com.moneymong.moneymong.model.ledgerdetail.LedgerTransactionDetailResponse

interface LedgerDetailRepository {
    suspend fun fetchLedgerTransactionDetail(detailId: Int): Result<LedgerTransactionDetailResponse>
    suspend fun postLedgerReceiptTransaction(detailId: Int, body: LedgerReceiptRequest): Result<Unit>
    suspend fun postLedgerDocumentTransaction(detailId: Int, body: LedgerDocumentRequest): Result<Unit>
    suspend fun updateLedgerTransactionDetail(detailId: Int, body: LedgerTransactionDetailRequest): Result<LedgerTransactionDetailResponse>
    suspend fun deleteLedgerReceiptTransaction(detailId: Int, receiptId: Int): Result<Unit>
    suspend fun deleteLedgerDocumentTransaction(detailId: Int, documentId: Int): Result<Unit>
    suspend fun deleteLedgerDetail(detailId: Int): Result<Unit>
}