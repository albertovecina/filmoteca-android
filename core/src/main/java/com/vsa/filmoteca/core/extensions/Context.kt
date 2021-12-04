package com.vsa.filmoteca.core.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.let { networkInfo ->
        networkInfo != null && networkInfo.isConnected
    } ?: false
}