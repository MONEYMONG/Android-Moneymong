package com.moneymong.moneymong.domain.usecase.ledger

import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchVisibleLedgerOnboardingUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {
    operator fun invoke(): Flow<Boolean> = ledgerRepository.fetchVisibleLedgerOnboarding()
}