package com.moneymong.moneymong.data.datasource.ledger

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class LedgerLocalDataSourceImpl @Inject constructor(
    @Named("ledger") private val ledgerPreferences: DataStore<Preferences>
) : LedgerLocalDataSource {
    private object Key {
        val LEDGER_ONBOARDING = booleanPreferencesKey("LEDGER_ONBOARDING")
    }

    private val visibleOnboarding: Flow<Boolean> =
        ledgerPreferences.data.map { preferences ->
            preferences[Key.LEDGER_ONBOARDING] ?: true
        }

    override fun fetchVisibleLedgerOnboarding(): Flow<Boolean> {
        return visibleOnboarding
    }

    override suspend fun postDisplayedLedgerOnboarding() {
        ledgerPreferences.edit { preferences ->
            preferences[Key.LEDGER_ONBOARDING] = false
        }
    }
}