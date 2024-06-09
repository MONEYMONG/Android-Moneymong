package com.moneymong.moneymong.data.datasource.ledger

import kotlinx.coroutines.flow.Flow

interface LedgerLocalDataSource {

    fun fetchVisibleLedgerOnboarding(): Flow<Boolean>

    suspend fun postDisplayedLedgerOnboarding()
}