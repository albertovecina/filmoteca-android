package com.vsa.filmoteca.presentation.interactor;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.presentation.utils.ConnectivityUtils;
import com.vsa.filmoteca.data.repository.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;


/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public class MainInteractor {

    private MoviesRepository mRepository;

    @Inject
    public MainInteractor(MoviesRepository moviesRepository) {
        mRepository = moviesRepository;
    }

    public boolean isNetworkAvailable() {
        return ConnectivityUtils.isInternetAvailable();
    }

    public Observable<List<Movie>> moviesList() {
        return mRepository.moviesList();
    }

    public Observable<String> movieDetail(String url) {
        return mRepository.movieDetail(url);
    }

}
