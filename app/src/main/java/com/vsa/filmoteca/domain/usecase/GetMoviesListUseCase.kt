package com.vsa.filmoteca.domain.usecase

import com.vsa.filmoteca.data.source.repository.MoviesDataRepository
import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.network.executor.AsyncExecutor
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

class GetMoviesListUseCase @Inject constructor(
    private val repository: MoviesDataRepository,
    private val asyncExecutor: AsyncExecutor
) {

    fun moviesList(callback: (Result<List<Movie>>) -> Unit) {
        asyncExecutor.execute({
            repository.moviesList()
        }, callback)
    }

}
