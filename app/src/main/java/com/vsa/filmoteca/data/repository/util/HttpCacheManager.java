package com.vsa.filmoteca.data.repository.util;

import android.content.Context;

import com.vsa.filmoteca.R;

import java.io.File;

/**
 * Created by albertovecinasanchez on 27/5/16.
 */
public class HttpCacheManager {

    public static File getHttpCacheDir(Context context) {
        File cacheDir = new File(context.getCacheDir(), context.getString(R.string.ws_cache_directory_name));
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir;
    }

    /**
     * Remove files from cache older than a given time in milliseconds. This method was created to
     * remove te retrofit cache file
     *
     * @param millis Deprecation time
     */
    public static void removeCacheFilesOlderThan(Context context, long millis) {
        long currentTime = System.currentTimeMillis();
        File cacheDir = getHttpCacheDir(context);
        boolean isExpired;
        if (cacheDir != null && cacheDir.listFiles() != null) {
            for (File file : cacheDir.listFiles()) {
                isExpired = (file.lastModified() + millis) < currentTime;
                if (isExpired)
                    file.delete();
            }
        }
    }

}
