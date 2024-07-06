package com.moneymong.moneymong.model.ledgerdetail

data class LedgerTransactionDetailRequest(
    val storeInfo: String,
    val amount: Int,
    val description: String,
    val paymentDate: String
)