package com.moneymong.moneymong.model.ledger

data class  LedgerTransactionRequest(
    val storeInfo: String,
    val fundType: String,
    val amount: Int,
    val description: String,
    val paymentDate: String,
    val documentImageUrls: List<String> = emptyList()
)
