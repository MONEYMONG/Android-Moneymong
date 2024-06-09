package com.moneymong.moneymong.data.datasource.ledger

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.moneymong.moneymong.model.ledger.OnboardingType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class LedgerLocalDataSourceImpl @Inject constructor(
    @Named("ledger") private val ledgerPreferences: DataStore<Preferences>
) : LedgerLocalDataSource {
    private object Key {
        val LEDGER_ONBOARDING_STAFF = booleanPreferencesKey("LEDGER_ONBOARDING_STAFF")
        val LEDGER_ONBOARDING_MEMBER = booleanPreferencesKey("LEDGER_ONBOARDING_MEMBER")
    }

    private val visibleOnboardingStaff: Flow<Boolean> =
        ledgerPreferences.data.map { preferences ->
            preferences[Key.LEDGER_ONBOARDING_STAFF] ?: true
        }

    private val visibleOnboardingMember: Flow<Boolean> =
        ledgerPreferences.data.map { preferences ->
            preferences[Key.LEDGER_ONBOARDING_MEMBER] ?: true
        }

    override fun fetchVisibleLedgerOnboarding(onboardingType: OnboardingType): Flow<Boolean> {
        return when (onboardingType) {
            OnboardingType.STAFF -> visibleOnboardingStaff
            OnboardingType.MEMBER -> visibleOnboardingMember
        }
    }

    override suspend fun postDisplayedLedgerOnboarding(onboardingType: OnboardingType) {
        ledgerPreferences.edit { preferences ->
            when (onboardingType) {
                OnboardingType.STAFF -> preferences[Key.LEDGER_ONBOARDING_STAFF] = false
                OnboardingType.MEMBER -> preferences[Key.LEDGER_ONBOARDING_MEMBER] = false
            }
        }
    }
}