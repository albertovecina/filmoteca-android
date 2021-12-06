package com.vsa.filmoteca.network.internal.di

import android.content.Context
import com.vsa.filmoteca.network.BuildConfig
import com.vsa.filmoteca.network.R
import com.vsa.filmoteca.network.domain.model.ApiClientBuilder
import com.vsa.filmoteca.network.domain.model.RetrofitApiClientBuilder
import com.vsa.filmoteca.network.interceptor.BasicAuthInterceptor
import com.vsa.filmoteca.network.interceptor.CacheRequestInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Alberto Vecina Sánchez on 2019-05-03.
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesCacheDirectory(@ApplicationContext context: Context): File = File(
        context.cacheDir.absolutePath, context.resources.getString(
            R.string.ws_cache_directory_name
        )
    )

    @Singleton
    @Provides
    fun providesCache(@ApplicationContext context: Context, cacheDirectory: File): Cache = Cache(
        cacheDirectory, context.resources.getString(
            R.string.ws_cache_size
        ).toLong()
    )

    @Singleton
    @Provides
    fun providesBasicAuthInterceptor() =
        BasicAuthInterceptor(BuildConfig.BASIC_AUTH_USER, BuildConfig.BASIC_AUTH_PASSWORD)

    @Singleton
    @Provides
    fun providesCacheRequestInterceptor(@ApplicationContext context: Context): CacheRequestInterceptor =
        CacheRequestInterceptor(context)

    @Singleton
    @Provides
    fun providesHttpClient(
        @ApplicationContext context: Context,
        cache: Cache,
        basicAuthInterceptor: BasicAuthInterceptor,
        cacheRequestInterceptor: CacheRequestInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .readTimeout(
            context.resources.getString(R.string.ws_timeout).toLong(),
            TimeUnit.MILLISECONDS
        )
        .addInterceptor(basicAuthInterceptor)
        .addInterceptor(cacheRequestInterceptor)
        .build()

    @Singleton
    @Provides
    fun providesRetrofitBuilder(httpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {

        @Binds
        fun bindsApiClientBuilder(apiClientBuilder: RetrofitApiClientBuilder): ApiClientBuilder

    }

}