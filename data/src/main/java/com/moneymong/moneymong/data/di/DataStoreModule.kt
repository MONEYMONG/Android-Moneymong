package com.moneymong.moneymong.data.di

import android.content.Context
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
    private val Context.ledgerDataStore by preferencesDataStore(name = LEDGER_DATASTORE_NAME)

    @Provides
    @Singleton
    @Named("ledger")
    fun provideLedgerDataStore(
        @ApplicationContext context: Context,
    ) = context.ledgerDataStore
}
