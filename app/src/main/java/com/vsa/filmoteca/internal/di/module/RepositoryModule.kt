package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.data.source.repository.AppConfigRepository
import com.vsa.filmoteca.data.source.repository.AppConfigRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesRemoteConfigRepository(repository: AppConfigRepositoryImpl): AppConfigRepository = repository

}