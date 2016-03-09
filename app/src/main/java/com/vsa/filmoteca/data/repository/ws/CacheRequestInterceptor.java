package com.vsa.filmoteca.data.repository.ws;

import com.vsa.filmoteca.presentation.utils.ConnectivityUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by albertovecinasanchez on 25/11/15.
 */
public class CacheRequestInterceptor implements Interceptor {

    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String LOAD_FROM_CACHE = "max-stale";
    private static final String LOAD_FROM_NETWORK = "max-age=0";
    private static final String LOAD_WITH_CACHE_PRIORITY = "max-stale=31536000";

    public enum CachePolicy {FORCE_CACHE_LOADING, FORCE_NETWORK_LOADING, DISABLE_CACHE, PRIORITY_NETWORK, PRIORITY_CACHE}

    private CachePolicy mCachePolicy = CachePolicy.PRIORITY_NETWORK;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request;
        switch (mCachePolicy) {

            case FORCE_CACHE_LOADING:
                request = chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_CACHE).build();
                break;
            case FORCE_NETWORK_LOADING:
                request = chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_NETWORK).build();
                break;
            case PRIORITY_NETWORK:
                if (ConnectivityUtils.isInternetAvailable())
                    request = chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_NETWORK).build();
                else
                    request = chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_CACHE).build();
                break;
            case PRIORITY_CACHE:
                request = chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_WITH_CACHE_PRIORITY).build();
                break;
            case DISABLE_CACHE:
            default:
                request = chain.request().newBuilder().addHeader(HEADER_CACHE_CONTROL, LOAD_FROM_CACHE).build();
        }
        return chain.proceed(request);
    }

    public void setCachePolicy(CachePolicy cachePolicy) {
        mCachePolicy = cachePolicy;
    }

}
