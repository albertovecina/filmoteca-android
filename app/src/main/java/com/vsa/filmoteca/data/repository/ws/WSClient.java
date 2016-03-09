package com.vsa.filmoteca.data.repository.ws;

import com.vsa.filmoteca.FilmotecaApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by albertovecinasanchez on 8/3/16.
 */
public class WSClient {

    private static final long TIMEOUT = 60000;
    private static final String CACHE_DIRECTORY = "HttpCache";


    private static WSInterface sRetrofitClient;
    private static CacheRequestInterceptor sCacheRequestInterceptor = new CacheRequestInterceptor();

    public static WSInterface getClient(CacheRequestInterceptor.CachePolicy cachePolicy) {
        if (sRetrofitClient == null) {
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            File cacheDirectory = new File(FilmotecaApplication.getInstance().getCacheDir().getAbsolutePath(), CACHE_DIRECTORY);
            Cache cache = new Cache(cacheDirectory, cacheSize);
            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(cache)
                    .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(sCacheRequestInterceptor)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://www.albacete.es/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            sRetrofitClient = retrofit.create(WSInterface.class);
        }
        if (sCacheRequestInterceptor != null)
            sCacheRequestInterceptor.setCachePolicy(cachePolicy);
        return sRetrofitClient;
    }

}
