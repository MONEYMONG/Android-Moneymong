package com.moneymong.moneymong.domain.repository.ledger

import com.moneymong.moneymong.model.ledger.LedgerTransactionListResponse
import com.moneymong.moneymong.model.ledger.LedgerTransactionRequest
import com.moneymong.moneymong.model.ledger.LedgerTransactionResponse
import com.moneymong.moneymong.model.ledger.OnboardingType
import kotlinx.coroutines.flow.Flow

interface LedgerRepository {
    suspend fun fetchLedgerTransactionList(id: Int, startYear: Int, startMonth: Int, endYear: Int, endMonth: Int, page: Int, limit: Int): Result<LedgerTransactionListResponse>
    suspend fun fetchAgencyExistLedger(agencyId: Int): Result<Boolean>
    suspend fun postLedgerTransaction(id: Int, request: LedgerTransactionRequest): Result<LedgerTransactionResponse>

    fun fetchVisibleLedgerOnboarding(onboardingType: OnboardingType): Flow<Boolean>
    suspend fun postDisplayedLedgerOnboarding(onboardingType: OnboardingType)
}