package com.vsa.filmoteca.data.source.repository

import android.content.Context

import com.vsa.filmoteca.R
import com.vsa.filmoteca.data.source.util.HttpCacheManager
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 27/5/16.
 */
class CacheRepository @Inject constructor(private val context: Context) {

    fun clearExpiredCacheFilesAsync() {
        val expirationDays = context.resources.getInteger(R.integer.cache_file_expiration_days)
        val expirationTime = expirationDays * 24 * 60 * 60 * 1000
        Thread { HttpCacheManager.removeCacheFilesOlderThan(context, expirationTime.toLong()) }.start()
    }

}
