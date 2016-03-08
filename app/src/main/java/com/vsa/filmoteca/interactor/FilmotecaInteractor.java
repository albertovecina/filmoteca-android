package com.vsa.filmoteca.interactor;

import com.vsa.filmoteca.model.domain.Movie;

import java.util.List;

import rx.Observable;

/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public interface FilmotecaInteractor {

    boolean isNetworkAvailable();

    Observable<List<Movie>> moviesList();

    Observable<String> movieDetail(String url);

}
