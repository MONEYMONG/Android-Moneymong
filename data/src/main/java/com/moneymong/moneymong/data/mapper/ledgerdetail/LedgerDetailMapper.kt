package com.moneymong.moneymong.data.mapper.ledgerdetail

import com.moneymong.moneymong.domain.entity.ledgerdetail.LedgerTransactionDetailEntity
import com.moneymong.moneymong.domain.param.ledgerdetail.LedgerDocumentParam
import com.moneymong.moneymong.domain.param.ledgerdetail.LedgerReceiptParam
import com.moneymong.moneymong.domain.param.ledgerdetail.LedgerTransactionDetailParam
import com.moneymong.moneymong.network.request.ledgerdetail.LedgerDocumentRequest
import com.moneymong.moneymong.network.request.ledgerdetail.LedgerReceiptRequest
import com.moneymong.moneymong.network.request.ledgerdetail.LedgerTransactionDetailRequest
import com.moneymong.moneymong.network.response.ledgerdetail.LedgerTransactionDetailResponse

fun LedgerReceiptParam.toRequest() =
    LedgerReceiptRequest(
        receiptImageUrls = this.receiptImageUrls
    )

fun LedgerDocumentParam.toRequest() =
    LedgerDocumentRequest(
        documentImageUrls = this.documentImageUrls
    )

fun LedgerTransactionDetailParam.toRequest() =
    LedgerTransactionDetailRequest(
        storeInfo = this.storeInfo,
        amount = this.amount,
        description = this.description,
        paymentDate = this.paymentDate
    )

fun LedgerTransactionDetailResponse.toEntity() =
    LedgerTransactionDetailEntity(
        id = this.id,
        storeInfo = this.storeInfo,
        amount = this.amount,
        fundType = this.fundType,
        description = this.description,
        paymentDate = this.paymentDate,
        receiptImageUrls = this.receiptImageUrls,
        documentImageUrls = this.documentImageUrls,
        authorName = this.authorName
    )