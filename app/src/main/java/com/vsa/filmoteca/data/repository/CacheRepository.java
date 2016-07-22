package com.vsa.filmoteca.data.repository;

import android.content.Context;

import com.vsa.filmoteca.R;
import com.vsa.filmoteca.data.repository.util.HttpCacheManager;

/**
 * Created by albertovecinasanchez on 27/5/16.
 */
public class CacheRepository {

    private Context mContext;

    public CacheRepository(Context context) {
        mContext = context;
    }

    public void clearExpiredCacheFilesAsync() {
        int expirationDays = mContext.getResources().getInteger(R.integer.cache_file_expiration_days);
        int expirationTime = expirationDays * 24 * 60 * 60 * 1000;
        new Thread(() -> {
            HttpCacheManager.removeCacheFilesOlderThan(mContext, expirationTime);
        }).start();
    }

}
