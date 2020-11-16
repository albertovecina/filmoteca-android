package com.vsa.filmoteca.domain.usecase

import com.vsa.filmoteca.data.source.repository.CacheRepository
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 18/7/16.
 */
class ClearCacheUseCase @Inject constructor(private val repository: CacheRepository) {

    fun clearExpiredCacheFiles() {
        repository.clearExpiredCacheFilesAsync()
    }

}
