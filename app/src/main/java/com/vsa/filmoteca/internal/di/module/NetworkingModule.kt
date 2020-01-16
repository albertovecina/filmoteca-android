package com.vsa.filmoteca.internal.di.module

import android.content.Context
import com.vsa.filmoteca.R
import com.vsa.filmoteca.data.repository.ws.CacheRequestInterceptor
import com.vsa.filmoteca.data.repository.ws.Environment
import com.vsa.filmoteca.data.repository.ws.FilmotecaInterface
import com.vsa.filmoteca.data.repository.ws.WsInterface
import com.vsa.filmoteca.internal.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Alberto Vecina Sánchez on 2019-05-03.
 */
@Module
class NetworkingModule {

    @PerApplication
    @Provides
    fun providesCacheDirectory(context: Context): File = File(context.cacheDir.absolutePath, context.resources.getString(R.string.ws_cache_directory_name))

    @PerApplication
    @Provides
    fun providesCache(context: Context, cacheDirectory: File): Cache = Cache(cacheDirectory, context.resources.getString(R.string.ws_cache_size).toLong())

    @PerApplication
    @Provides
    fun providesCacheRequestInterceptor(context: Context): CacheRequestInterceptor = CacheRequestInterceptor(context)

    @PerApplication
    @Provides
    fun providesHttpClient(context: Context, cache: Cache, cacheRequestInterceptor: CacheRequestInterceptor): OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(context.resources.getString(R.string.ws_timeout).toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(cacheRequestInterceptor)
            .build()

    @PerApplication
    @Provides
    fun providesRetrofitBuilder(httpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
            .client(httpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())

    @PerApplication
    @Provides
    fun providesFilmotecaInterface(retrofitBuilder: Retrofit.Builder): FilmotecaInterface =
            retrofitBuilder
                    .baseUrl(Environment.BASE_URL_FILMOTECA)
                    .build()
                    .create(FilmotecaInterface::class.java)

    @PerApplication
    @Provides
    fun providesWsInterface(retrofitBuilder: Retrofit.Builder): WsInterface =
            retrofitBuilder
                    .baseUrl(Environment.BASE_URL_WS)
                    .build()
                    .create(WsInterface::class.java)

}