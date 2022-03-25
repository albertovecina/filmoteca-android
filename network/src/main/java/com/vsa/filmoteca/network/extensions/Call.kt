package com.vsa.filmoteca.network.extensions

import retrofit2.Call

fun <T> Call<T>.run(): Result<T> =
    try {
        execute().toResult()
    } catch (e: Exception) {
        Result.failure(e)
    }
