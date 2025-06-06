package com.moneymong.moneymong.domain.usecase.ledgerdetail

import com.moneymong.moneymong.domain.repository.ledgerdetail.LedgerDetailRepository
import javax.inject.Inject

class DeleteLedgerDetailUseCase
    @Inject
    constructor(
        private val ledgerDetailRepository: LedgerDetailRepository,
    ) {
        suspend operator fun invoke(detailId: Int): Result<Unit> = ledgerDetailRepository.deleteLedgerDetail(detailId)
    }
