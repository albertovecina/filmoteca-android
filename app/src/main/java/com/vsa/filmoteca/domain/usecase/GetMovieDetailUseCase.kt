package com.vsa.filmoteca.domain.usecase

import com.vsa.filmoteca.data.source.repository.MoviesDataRepository
import com.vsa.filmoteca.network.executor.AsyncExecutor
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MoviesDataRepository,
    private val asyncExecutor: AsyncExecutor
) {

    fun movieDetail(url: String, callback: (Result<String>) -> Unit) {
        asyncExecutor.execute({
            repository.movieDetail(url)
        }, callback)
    }

}
