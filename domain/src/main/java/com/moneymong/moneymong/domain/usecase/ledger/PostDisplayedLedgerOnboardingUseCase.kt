package com.moneymong.moneymong.domain.usecase.ledger

import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import com.moneymong.moneymong.model.ledger.OnboardingType
import javax.inject.Inject

class PostDisplayedLedgerOnboardingUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {
    suspend operator fun invoke(onboardingType: OnboardingType) {
        ledgerRepository.postDisplayedLedgerOnboarding(onboardingType = onboardingType)
    }
}