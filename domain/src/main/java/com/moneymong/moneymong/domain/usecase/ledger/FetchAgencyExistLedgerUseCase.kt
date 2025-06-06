package com.moneymong.moneymong.domain.usecase.ledger

import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import javax.inject.Inject

class FetchAgencyExistLedgerUseCase
    @Inject
    constructor(
        private val ledgerRepository: LedgerRepository,
    ) {
        suspend operator fun invoke(agencyId: Int): Result<Boolean> = ledgerRepository.fetchAgencyExistLedger(agencyId)
    }
