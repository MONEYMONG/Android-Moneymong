package com.moneymong.moneymong.model.ledgerdetail

import com.moneymong.moneymong.model.ledger.DocumentImageURL
import com.moneymong.moneymong.model.ledger.ReceiptImageURL

data class LedgerTransactionDetailResponse(
    val id: Int,
    val storeInfo: String,
    val amount: Int,
    val fundType: String,
    val description: String,
    val paymentDate: String,
    val receiptImageUrls: List<ReceiptImageURL>,
    val documentImageUrls: List<DocumentImageURL>,
    val authorName: String
)