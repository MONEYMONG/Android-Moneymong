package com.moneymong.moneymong.data.repository.ledgerdetail

import com.moneymong.moneymong.data.datasource.ledgerdetail.LedgerDetailRemoteDataSource
import com.moneymong.moneymong.domain.repository.ledgerdetail.LedgerDetailRepository
import com.moneymong.moneymong.model.ledgerdetail.LedgerDocumentRequest
import com.moneymong.moneymong.model.ledgerdetail.LedgerReceiptRequest
import com.moneymong.moneymong.model.ledgerdetail.LedgerTransactionDetailRequest
import com.moneymong.moneymong.model.ledgerdetail.LedgerTransactionDetailResponse
import javax.inject.Inject

class LedgerDetailRepositoryImpl @Inject constructor(
    private val ledgerDetailRemoteDataSource: LedgerDetailRemoteDataSource
) : LedgerDetailRepository {
    override suspend fun fetchLedgerTransactionDetail(detailId: Int): Result<LedgerTransactionDetailResponse> =
        ledgerDetailRemoteDataSource.fetchLedgerTransactionDetail(detailId = detailId)

    override suspend fun postLedgerReceiptTransaction(detailId: Int, body: LedgerReceiptRequest): Result<Unit> =
        ledgerDetailRemoteDataSource.postLedgerReceiptTransaction(
            detailId = detailId,
            body = body
        )

    override suspend fun postLedgerDocumentTransaction(detailId: Int, body: LedgerDocumentRequest): Result<Unit> =
        ledgerDetailRemoteDataSource.postLedgerDocumentTransaction(
            detailId = detailId,
            body = body
        )

    override suspend fun updateLedgerTransactionDetail(detailId: Int, body: LedgerTransactionDetailRequest): Result<LedgerTransactionDetailResponse> =
        ledgerDetailRemoteDataSource.updateLedgerTransactionDetail(
            detailId = detailId,
            body = body
        )

    override suspend fun deleteLedgerReceiptTransaction(detailId: Int, receiptId: Int): Result<Unit> =
        ledgerDetailRemoteDataSource.deleteLedgerReceiptTransaction(
            detailId = detailId,
            receiptId = receiptId
        )

    override suspend fun deleteLedgerDocumentTransaction(detailId: Int, documentId: Int): Result<Unit> =
        ledgerDetailRemoteDataSource.deleteLedgerDocumentTransaction(
            detailId = detailId,
            documentId = documentId
        )

    override suspend fun deleteLedgerDetail(detailId: Int): Result<Unit> =
        ledgerDetailRemoteDataSource.deleteLedgerDetail(detailId = detailId)
}