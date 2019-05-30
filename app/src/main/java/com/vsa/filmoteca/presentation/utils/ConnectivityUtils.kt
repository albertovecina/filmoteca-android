package com.vsa.filmoteca.presentation.utils

import android.content.Context
import android.net.ConnectivityManager

object ConnectivityUtils {

    fun isInternetAvailable(c: Context): Boolean {
        val cm = c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        // if no network is available networkInfo will be null, otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected
    }

}
