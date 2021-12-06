package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.BuildConfig
import com.vsa.filmoteca.data.source.ws.FilmotecaInterface
import com.vsa.filmoteca.data.source.ws.WsInterface
import com.vsa.filmoteca.network.domain.model.ApiClientBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun providesFilmotecaInterface(apiClientBuilder: ApiClientBuilder): FilmotecaInterface =
        apiClientBuilder
            .baseUrl(BuildConfig.BASE_URL_FILMOTECA)
            .create(FilmotecaInterface::class.java)

    @Singleton
    @Provides
    fun providesWsInterface(apiClientBuilder: ApiClientBuilder): WsInterface =
        apiClientBuilder
            .baseUrl(BuildConfig.BASE_URL_WS)
            .create(WsInterface::class.java)

}