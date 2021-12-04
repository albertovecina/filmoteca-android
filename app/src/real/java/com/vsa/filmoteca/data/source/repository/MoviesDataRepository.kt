package com.vsa.filmoteca.data.source.repository

import com.vsa.filmoteca.data.source.ws.FilmotecaInterface
import com.vsa.filmoteca.domain.mapper.DetailHtmlParser
import com.vsa.filmoteca.domain.mapper.MovieHtmlMapper
import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.network.extensions.toResult
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 7/12/15.
 */
class MoviesDataRepository @Inject constructor(private val filmotecaInterface: FilmotecaInterface) {

    fun moviesList(): Result<List<Movie>> =
        filmotecaInterface.moviesListHtml().execute().toResult()
            .map { MovieHtmlMapper.transformMovie(it) }


    fun movieDetail(url: String): Result<String> =
        with(filmotecaInterface.movieDetail(url).execute().toResult()) {
            DetailHtmlParser.parse(getOrNull())?.let {
                Result.success(it)
            } ?: Result.failure(Exception())
        }

}