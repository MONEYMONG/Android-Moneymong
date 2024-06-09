package com.moneymong.moneymong.domain.usecase.ledger

import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import javax.inject.Inject

class PostDisplayedLedgerOnboardingUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {
    suspend operator fun invoke() {
        ledgerRepository.postDisplayedLedgerOnboarding()
    }
}