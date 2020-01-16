package com.vsa.filmoteca.domain.usecase

import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.data.source.repository.MoviesDataRepository

import rx.Observable
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

class GetMoviesListUseCase @Inject constructor(private val repository: MoviesDataRepository) {

    fun moviesList(): Observable<List<Movie>> {
        return repository.moviesList()
    }

}
