package com.vsa.filmoteca.network.domain.model

import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitApiClientBuilder @Inject constructor(private val retrofitBuilder: Retrofit.Builder) :
    ApiClientBuilder {

    override fun baseUrl(baseUrl: String): ApiClientBuilder {
        retrofitBuilder.baseUrl(baseUrl)
        return this
    }

    override fun <T> create(service: Class<T>): T =
        retrofitBuilder.build().create(service)

}