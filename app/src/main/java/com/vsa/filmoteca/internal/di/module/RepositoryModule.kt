package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.data.source.repository.AppConfigRepository
import com.vsa.filmoteca.data.source.repository.AppConfigRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsRemoteConfigRepository(repository: AppConfigRepositoryImpl): AppConfigRepository

}