package com.vsa.filmoteca.data.repository;

import com.vsa.filmoteca.FilmotecaApplication;
import com.vsa.filmoteca.R;
import com.vsa.filmoteca.data.repository.util.CacheManager;

/**
 * Created by albertovecinasanchez on 27/5/16.
 */
public class CacheRepository {

    public void clearExpiredCacheFiles() {
        int expirationDays = FilmotecaApplication.getInstance().getResources().getInteger(R.integer.cache_file_expiration_days);
        int expirationTime = expirationDays * 24 * 60 * 60 * 1000;
        CacheManager.removeCacheFilesOlderThan(expirationTime);
    }

}
