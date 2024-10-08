package com.moneymong.moneymong.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val LEDGER_DATASTORE_NAME = "LEDGER_PREFERENCES"
    private const val USER_DATASTORE_NAME = "USER_PREFERENCES"

    private val Context.ledgerDataStore: DataStore<Preferences> by preferencesDataStore(name = LEDGER_DATASTORE_NAME)
    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE_NAME)

    @Provides
    @Singleton
    @Named("ledger")
    fun provideLedgerPreferencesDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.ledgerDataStore

    @Provides
    @Singleton
    @Named("user")
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.userDataStore
}
