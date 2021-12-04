package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.BuildConfig
import com.vsa.filmoteca.data.source.ws.FilmotecaInterface
import com.vsa.filmoteca.data.source.ws.WsInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun providesFilmotecaInterface(retrofitBuilder: Retrofit.Builder): FilmotecaInterface =
        retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL_FILMOTECA)
            .build()
            .create(FilmotecaInterface::class.java)

    @Singleton
    @Provides
    fun providesWsInterface(retrofitBuilder: Retrofit.Builder): WsInterface =
        retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL_WS)
            .build()
            .create(WsInterface::class.java)

}