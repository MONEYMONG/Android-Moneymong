package com.moneymong.moneymong.data.datasource.ledger

import com.moneymong.moneymong.model.ledger.OnboardingType
import kotlinx.coroutines.flow.Flow

interface LedgerLocalDataSource {
    fun fetchVisibleLedgerOnboarding(onboardingType: OnboardingType): Flow<Boolean>

    suspend fun postDisplayedLedgerOnboarding(onboardingType: OnboardingType)
}
