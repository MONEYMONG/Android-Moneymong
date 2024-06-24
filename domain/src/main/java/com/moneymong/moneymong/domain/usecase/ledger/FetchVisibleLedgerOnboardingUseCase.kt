package com.moneymong.moneymong.domain.usecase.ledger

import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import com.moneymong.moneymong.model.ledger.OnboardingType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchVisibleLedgerOnboardingUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {
    operator fun invoke(onboardingType: OnboardingType): Flow<Boolean> =
        ledgerRepository.fetchVisibleLedgerOnboarding(onboardingType = onboardingType)
}