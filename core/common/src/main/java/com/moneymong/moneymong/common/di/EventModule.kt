package com.moneymong.moneymong.common.di

import com.moneymong.moneymong.common.event.EventTracker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventModule {

    @Provides
    @Singleton
    fun provideEventTracker(): EventTracker =
        EventTracker()
}