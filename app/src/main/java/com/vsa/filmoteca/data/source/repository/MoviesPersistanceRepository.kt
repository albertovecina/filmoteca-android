package com.vsa.filmoteca.data.source.repository

import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.data.source.database.MoviesDataBaseSource
import com.vsa.filmoteca.data.source.sharedpreferences.SharedPreferencesManager

import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 22/7/16.
 */

class MoviesPersistanceRepository @Inject constructor(private val moviesDataBase: MoviesDataBaseSource,
                                                      private val sharedPreferencesManager: SharedPreferencesManager) {

    var currentMovieIndex: Int
        get() = sharedPreferencesManager.currentMovieIndex
        set(index) {
            sharedPreferencesManager.currentMovieIndex = index
        }

    var moviesCount: Int
        get() = sharedPreferencesManager.moviesCount
        set(size) {
            sharedPreferencesManager.moviesCount = size
        }

    val currentMovie: Movie
        get() {
            moviesDataBase.open()
            val movie = moviesDataBase.getMovie(currentMovieIndex)
            moviesDataBase.close()
            return movie
        }

    fun setMovies(movies: List<Movie>) {
        moviesDataBase.open()
        moviesDataBase.clearMovies()
        for (x in movies.indices)
            moviesDataBase.insertMovie(x, movies[x])
        moviesDataBase.close()
    }

}
