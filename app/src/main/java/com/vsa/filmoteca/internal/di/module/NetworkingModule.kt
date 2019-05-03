package com.vsa.filmoteca.internal.di.module

import android.content.Context
import com.vsa.filmoteca.R
import com.vsa.filmoteca.data.repository.ws.CacheRequestInterceptor
import com.vsa.filmoteca.data.repository.ws.Environment
import com.vsa.filmoteca.data.repository.ws.FilmotecaInterface
import com.vsa.filmoteca.internal.di.PerApplication
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
class NetworkingModule(private val context: Context) {

    @PerApplication
    @Provides
    fun providesCacheDirectory(): File = File(context.cacheDir.absolutePath, context.resources.getString(R.string.ws_cache_directory_name))

    @PerApplication
    @Provides
    fun providesCache(cacheDirectory: File): Cache = Cache(cacheDirectory, context.resources.getString(R.string.ws_cache_size).toLong())

    @PerApplication
    @Provides
    fun providesCacheRequestInterceptor(): CacheRequestInterceptor = CacheRequestInterceptor()

    @PerApplication
    @Provides
    fun providesHttpClient(cache: Cache, cacheRequestInterceptor: CacheRequestInterceptor): OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(context.resources.getString(R.string.ws_timeout).toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(cacheRequestInterceptor)
            .build()

    @PerApplication
    @Provides
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(Environment.BASE_URL_FILMOTECA)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    @PerApplication
    @Provides
    fun providesFilmotecaInterface(retrofit: Retrofit): FilmotecaInterface = retrofit.create(FilmotecaInterface::class.java)

}