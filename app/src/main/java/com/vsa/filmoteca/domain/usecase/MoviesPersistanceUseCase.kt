package com.vsa.filmoteca.domain.usecase

import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.data.source.repository.MoviesPersistanceRepository
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 22/7/16.
 */

class MoviesPersistanceUseCase @Inject constructor(private val repository: MoviesPersistanceRepository) {

    var currentMovieIndex: Int
        get() = repository.currentMovieIndex
        set(index) {
            repository.currentMovieIndex = index
        }

    var moviesCount: Int
        get() = repository.moviesCount
        set(size) {
            repository.moviesCount = size
        }

    val currentMovie: Movie
        get() = repository.currentMovie

    fun setMovies(movies: List<Movie>) {
        repository.setMovies(movies)
    }

}
