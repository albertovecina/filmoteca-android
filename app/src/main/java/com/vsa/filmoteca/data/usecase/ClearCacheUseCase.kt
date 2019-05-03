package com.vsa.filmoteca.data.usecase

import com.vsa.filmoteca.data.repository.CacheRepository
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 18/7/16.
 */
class ClearCacheUseCase @Inject constructor(private val repository: CacheRepository) {

    fun clearExpiredCacheFiles() {
        repository.clearExpiredCacheFilesAsync()
    }

}
