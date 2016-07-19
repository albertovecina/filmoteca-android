package com.vsa.filmoteca.data.usecase;

import com.vsa.filmoteca.data.repository.CacheRepository;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

public class ClearCacheUseCase {

    private CacheRepository mCacheRepository;

    public ClearCacheUseCase(CacheRepository cacheRepository) {
        mCacheRepository = cacheRepository;
    }

    public void clearExpiredCacheFiles() {
        mCacheRepository.clearExpiredCacheFiles();
    }

}
