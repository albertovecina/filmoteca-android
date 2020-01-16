package com.vsa.filmoteca.data.source.repository

import android.content.Context
import com.vsa.filmoteca.presentation.utils.ConnectivityUtils
import javax.inject.Inject

/**
 * Created by Alberto Vecina Sánchez on 2019-05-16.
 */
class SystemDataRepository @Inject constructor(private val context: Context) {

    fun isInternetAvailable():Boolean = ConnectivityUtils.isInternetAvailable(context)

}