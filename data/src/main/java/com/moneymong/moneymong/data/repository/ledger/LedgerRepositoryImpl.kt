package com.moneymong.moneymong.data.repository.ledger

import com.moneymong.moneymong.data.datasource.ledger.LedgerLocalDataSource
import com.moneymong.moneymong.data.datasource.ledger.LedgerRemoteDataSource
import com.moneymong.moneymong.domain.repository.ledger.LedgerRepository
import com.moneymong.moneymong.model.ledger.LedgerTransactionListResponse
import com.moneymong.moneymong.model.ledger.LedgerTransactionRequest
import com.moneymong.moneymong.model.ledger.LedgerTransactionResponse
import com.moneymong.moneymong.model.ledger.OnboardingType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LedgerRepositoryImpl
    @Inject
    constructor(
        private val ledgerRemoteDataSource: LedgerRemoteDataSource,
        private val ledgerLocalDataSource: LedgerLocalDataSource,
    ) : LedgerRepository {
        override suspend fun fetchLedgerTransactionList(
            id: Int,
            startYear: Int,
            startMonth: Int,
            endYear: Int,
            endMonth: Int,
            page: Int,
            limit: Int,
        ): Result<LedgerTransactionListResponse> =
            ledgerRemoteDataSource.fetchLedgerTransactionList(
                id = id,
                startYear = startYear,
                startMonth = startMonth,
                endYear = endYear,
                endMonth = endMonth,
                page = page,
                limit = limit,
            )

        override suspend fun fetchAgencyExistLedger(agencyId: Int): Result<Boolean> =
            ledgerRemoteDataSource.fetchAgencyExistLedger(agencyId = agencyId)

        override suspend fun postLedgerTransaction(
            id: Int,
            request: LedgerTransactionRequest,
        ): Result<LedgerTransactionResponse> = ledgerRemoteDataSource.postLedgerTransaction(id = id, body = request)

        override fun fetchVisibleLedgerOnboarding(onboardingType: OnboardingType): Flow<Boolean> {
            return ledgerLocalDataSource.fetchVisibleLedgerOnboarding(onboardingType = onboardingType)
        }

        override suspend fun postDisplayedLedgerOnboarding(onboardingType: OnboardingType) {
            ledgerLocalDataSource.postDisplayedLedgerOnboarding(onboardingType = onboardingType)
        }
    }
