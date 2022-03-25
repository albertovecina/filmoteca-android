package com.vsa.filmoteca.network.extensions

import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T> {
    return if (isSuccessful)
        body()?.let { Result.success(it) } ?: Result.failure(Exception())
    else
        Result.failure(Exception())
}
