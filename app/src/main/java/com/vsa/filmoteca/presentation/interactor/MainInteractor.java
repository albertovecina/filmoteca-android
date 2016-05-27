package com.vsa.filmoteca.presentation.interactor;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.data.repository.CacheRepository;
import com.vsa.filmoteca.presentation.utils.ConnectivityUtils;
import com.vsa.filmoteca.data.repository.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;


/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public class MainInteractor {

    private MoviesRepository mMoviesRepository;
    private CacheRepository mCacheRepository;

    @Inject
    public MainInteractor(MoviesRepository moviesRepository, CacheRepository cacheRepository) {
        mMoviesRepository = moviesRepository;
        mCacheRepository = cacheRepository;
    }

    public boolean isNetworkAvailable() {
        return ConnectivityUtils.isInternetAvailable();
    }

    public Observable<List<Movie>> moviesList() {
        return mMoviesRepository.moviesList();
    }

    public Observable<String> movieDetail(String url) {
        return mMoviesRepository.movieDetail(url);
    }

    public void clearExpiredCacheFiles() {
        mCacheRepository.clearExpiredCacheFiles();
    }

}
