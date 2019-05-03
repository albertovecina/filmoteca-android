package com.vsa.filmoteca.data.repository.ws

import com.vsa.filmoteca.presentation.utils.ConnectivityUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 25/11/15.
 */
class CacheRequestInterceptor: Interceptor {

    private var mCachePolicy = CachePolicy.PRIORITY_NETWORK

    enum class CachePolicy {
        FORCE_CACHE_LOADING, FORCE_NETWORK_LOADING, DISABLE_CACHE, PRIORITY_NETWORK, PRIORITY_CACHE
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = when (mCachePolicy) {

            CachePolicy.FORCE_CACHE_LOADING -> chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_CACHE).build()
            CachePolicy.FORCE_NETWORK_LOADING -> chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_NETWORK).build()
            CachePolicy.PRIORITY_NETWORK -> if (ConnectivityUtils.isInternetAvailable())
                chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_NETWORK).build()
            else
                chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_CACHE).build()
            CachePolicy.PRIORITY_CACHE -> chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_WITH_CACHE_PRIORITY).build()
            CachePolicy.DISABLE_CACHE -> chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_CACHE).build()
        }
        return chain.proceed(request)
    }

    fun setCachePolicy(cachePolicy: CachePolicy) {
        mCachePolicy = cachePolicy
    }

    companion object {

        private const val HEADER_CACHE_CONTROL = "Cache-Control"
        private const val LOAD_FROM_CACHE = "max-stale"
        private const val LOAD_FROM_NETWORK = "max-age=0"
        private const val LOAD_WITH_CACHE_PRIORITY = "max-stale=31536000"
    }

}
