package com.vsa.filmoteca.repository.ws;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by albertovecinasanchez on 8/3/16.
 */
public class WSClient {

    private static WSInterface sRetrofitClient;


    public static WSInterface getClient() {
        if (sRetrofitClient == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.albacete.es/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            sRetrofitClient = retrofit.create(WSInterface.class);
        }
        return sRetrofitClient;
    }

}
