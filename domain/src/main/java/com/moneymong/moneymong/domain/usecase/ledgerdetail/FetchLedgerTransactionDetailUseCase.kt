package com.moneymong.moneymong.domain.usecase.ledgerdetail

import com.moneymong.moneymong.domain.base.BaseUseCase
import com.moneymong.moneymong.domain.repository.ledgerdetail.LedgerDetailRepository
import com.moneymong.moneymong.model.ledgerdetail.LedgerTransactionDetailResponse
import javax.inject.Inject

class FetchLedgerTransactionDetailUseCase @Inject constructor(
    private val ledgerDetailRepository: LedgerDetailRepository
): BaseUseCase<Int, Result<LedgerTransactionDetailResponse>>() {
    override suspend fun invoke(data: Int): Result<LedgerTransactionDetailResponse> =
        ledgerDetailRepository.fetchLedgerTransactionDetail(data)
}