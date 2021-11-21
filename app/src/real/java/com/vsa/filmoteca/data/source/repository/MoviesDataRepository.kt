package com.vsa.filmoteca.data.source.repository

import com.vsa.filmoteca.data.extensions.toResult
import com.vsa.filmoteca.data.source.ws.FilmotecaInterface
import com.vsa.filmoteca.domain.mapper.DetailHtmlParser
import com.vsa.filmoteca.domain.mapper.MovieHtmlMapper
import com.vsa.filmoteca.domain.model.Movie
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by albertovecinasanchez on 7/12/15.
 */
class MoviesDataRepository @Inject constructor(private val filmotecaInterface: FilmotecaInterface) {

    fun moviesList(): Observable<List<Movie>> {
        return filmotecaInterface.moviesListHtml().map { MovieHtmlMapper.transformMovie(it) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun movieDetail(url: String): Result<String> =
        with(filmotecaInterface.movieDetail(url).execute().toResult()) {
            DetailHtmlParser.parse(getOrNull())?.let {
                Result.success(it)
            } ?: Result.failure(Exception())
        }

}