package com.vsa.filmoteca.interactor;

import com.vsa.filmoteca.model.domain.Movie;
import com.vsa.filmoteca.presenter.utils.NetworkUtils;
import com.vsa.filmoteca.repository.DataRepository;

import java.util.List;

import rx.Observable;


/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public class FilmotecaInteractorImpl implements FilmotecaInteractor {

    private DataRepository mRepository = DataRepository.getInstance();

    @Override
    public boolean isNetworkAvailable() {
        return NetworkUtils.isNetworkAvailable();
    }

    @Override
    public Observable<List<Movie>> moviesList() {
        return mRepository.moviesList();
    }

    @Override
    public Observable<String> movieDetail(String url) {
        return mRepository.movieDetail(url);
    }

}
