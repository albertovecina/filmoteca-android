package com.vsa.filmoteca.data.source.repository

import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.domain.mapper.DetailHtmlParser
import com.vsa.filmoteca.domain.mapper.MovieHtmlMapper
import com.vsa.filmoteca.data.source.ws.FilmotecaInterface
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

    fun movieDetail(url: String): Observable<String> {
        return filmotecaInterface.movieDetail(url).map<String> { html ->
            try {
                DetailHtmlParser.parse(html)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
