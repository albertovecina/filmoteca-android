package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.data.source.repository.AppConfigRepository
import com.vsa.filmoteca.data.source.repository.AppConfigRepositoryImpl
import com.vsa.filmoteca.internal.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @PerApplication
    @Provides
    fun providesRemoteConfigRepository(repository: AppConfigRepositoryImpl): AppConfigRepository = repository

}