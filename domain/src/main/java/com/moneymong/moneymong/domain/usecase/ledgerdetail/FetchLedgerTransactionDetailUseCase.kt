package com.moneymong.moneymong.domain.usecase.ledgerdetail

import com.moneymong.moneymong.domain.repository.ledgerdetail.LedgerDetailRepository
import com.moneymong.moneymong.model.ledgerdetail.LedgerTransactionDetailResponse
import javax.inject.Inject

class FetchLedgerTransactionDetailUseCase
    @Inject
    constructor(
        private val ledgerDetailRepository: LedgerDetailRepository,
    ) {
        suspend operator fun invoke(detailId: Int): Result<LedgerTransactionDetailResponse> =
            ledgerDetailRepository.fetchLedgerTransactionDetail(detailId)
    }
