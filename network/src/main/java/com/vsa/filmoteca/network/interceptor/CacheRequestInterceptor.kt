package com.vsa.filmoteca.network.interceptor

import android.content.Context
import com.vsa.filmoteca.core.extensions.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by albertovecinasanchez on 25/11/15.
 */
class CacheRequestInterceptor(private val context: Context) : Interceptor {

    var cachePolicy: CachePolicy = CachePolicy.PriorityNetwork

    sealed class CachePolicy {
        object ForceCacheLoading : CachePolicy()
        object ForceNetworkLoading : CachePolicy()
        object DisableCache : CachePolicy()
        object PriorityNetwork : CachePolicy()
        object PriorityCache : CachePolicy()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = when (cachePolicy) {

            CachePolicy.ForceCacheLoading -> chain.request().newBuilder().addHeader(
                HEADER_CACHE_CONTROL, LOAD_FROM_CACHE
            ).build()
            CachePolicy.ForceNetworkLoading -> chain.request().newBuilder().addHeader(
                HEADER_CACHE_CONTROL, LOAD_FROM_NETWORK
            ).build()
            CachePolicy.PriorityNetwork -> if (context.isInternetAvailable())
                chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_NETWORK)
                    .build()
            else
                chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_CACHE)
                    .build()
            CachePolicy.PriorityCache -> chain.request().newBuilder().addHeader(
                HEADER_CACHE_CONTROL, LOAD_WITH_CACHE_PRIORITY
            ).build()
            CachePolicy.DisableCache -> chain.request().newBuilder()
                .addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_CACHE).build()
        }
        return chain.proceed(request)
    }

    companion object {
        private const val HEADER_CACHE_CONTROL = "Cache-Control"
        private const val LOAD_FROM_CACHE = "max-stale"
        private const val LOAD_FROM_NETWORK = "max-age=0"
        private const val LOAD_WITH_CACHE_PRIORITY = "max-stale=31536000"
    }

}
