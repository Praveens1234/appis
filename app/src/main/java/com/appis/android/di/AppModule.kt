package com.appis.android.di

import com.appis.android.data.real.RealOrchestrator
import com.appis.android.domain.repository.OrchestratorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindOrchestrator(
        impl: RealOrchestrator
    ): OrchestratorRepository
}
