package com.vsa.filmoteca.network.domain.model

interface ApiClientBuilder {

    fun baseUrl(baseUrl: String): ApiClientBuilder

    fun <T> create(service: Class<T>): T

}