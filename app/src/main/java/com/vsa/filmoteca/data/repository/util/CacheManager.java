package com.vsa.filmoteca.data.repository.util;

import android.content.Context;

import com.vsa.filmoteca.FilmotecaApplication;
import com.vsa.filmoteca.data.repository.ws.WSClient;

import java.io.File;

/**
 * Created by albertovecinasanchez on 27/5/16.
 */
public class CacheManager {

    /**
     * Remove files from cache older than a given time in milliseconds. This method was created to
     * remove te retrofit cache file
     *
     * @param millis Deprecation time
     */
    public static void removeCacheFilesOlderThan(Context context, long millis) {
        new Thread(() -> {
            long currentTime = System.currentTimeMillis();
            File cacheDir = new File(context.getCacheDir(), WSClient.CACHE_DIRECTORY);
            boolean isExpired;
            for (File file : cacheDir.listFiles()) {
                isExpired = (file.lastModified() + millis) < currentTime;
                if (isExpired)
                    file.delete();
            }
        }).start();
    }

}
