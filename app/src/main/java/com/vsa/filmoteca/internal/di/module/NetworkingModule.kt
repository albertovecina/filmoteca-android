package com.vsa.filmoteca.internal.di.module

import android.content.Context
import com.vsa.filmoteca.BuildConfig
import com.vsa.filmoteca.R
import com.vsa.filmoteca.data.source.ws.BasicAuthInterceptor
import com.vsa.filmoteca.data.source.ws.CacheRequestInterceptor
import com.vsa.filmoteca.data.source.ws.FilmotecaInterface
import com.vsa.filmoteca.data.source.ws.WsInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Alberto Vecina Sánchez on 2019-05-03.
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {

    @Singleton
    @Provides
    fun providesCacheDirectory(@ApplicationContext context: Context): File = File(context.cacheDir.absolutePath, context.resources.getString(R.string.ws_cache_directory_name))

    @Singleton
    @Provides
    fun providesCache(@ApplicationContext context: Context, cacheDirectory: File): Cache = Cache(cacheDirectory, context.resources.getString(R.string.ws_cache_size).toLong())

    @Singleton
    @Provides
    fun providesBasicAuthInterceptor() = BasicAuthInterceptor(BuildConfig.BASIC_AUTH_USER, BuildConfig.BASIC_AUTH_PASSWORD)

    @Singleton
    @Provides
    fun providesCacheRequestInterceptor(@ApplicationContext context: Context): CacheRequestInterceptor = CacheRequestInterceptor(context)

    @Singleton
    @Provides
    fun providesHttpClient(@ApplicationContext context: Context,
                           cache: Cache,
                           basicAuthInterceptor: BasicAuthInterceptor,
                           cacheRequestInterceptor: CacheRequestInterceptor): OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(context.resources.getString(R.string.ws_timeout).toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(basicAuthInterceptor)
            .addInterceptor(cacheRequestInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesRetrofitBuilder(httpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
            .client(httpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())

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