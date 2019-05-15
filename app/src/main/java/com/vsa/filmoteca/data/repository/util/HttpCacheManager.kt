package com.vsa.filmoteca.data.repository.util

import android.content.Context

import com.vsa.filmoteca.R

import java.io.File

/**
 * Created by albertovecinasanchez on 27/5/16.
 */
object HttpCacheManager {

    fun getHttpCacheDir(context: Context): File {
        val cacheDir = File(context.cacheDir, context.getString(R.string.ws_cache_directory_name))
        if (!cacheDir.exists())
            cacheDir.mkdirs()
        return cacheDir
    }

    /**
     * Remove files from cache older than a given time in milliseconds. This method was created to
     * remove te retrofit cache file
     *
     * @param millis Deprecation time
     */
    fun removeCacheFilesOlderThan(context: Context, millis: Long) {
        val currentTime = System.currentTimeMillis()
        val cacheDir = getHttpCacheDir(context)
        var isExpired: Boolean
        if (cacheDir.listFiles() != null) {
            for (file in cacheDir.listFiles()) {
                isExpired = file.lastModified() + millis < currentTime
                if (isExpired)
                    file.delete()
            }
        }
    }

}
