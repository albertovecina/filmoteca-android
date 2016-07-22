package com.vsa.filmoteca.data.usecase;

import com.vsa.filmoteca.data.repository.MoviesDataRepository;

import rx.Observable;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

public class GetMovieDetailUseCase {

    private MoviesDataRepository mMoviesDataRepository;

    public GetMovieDetailUseCase(MoviesDataRepository mMoviesDataRepository) {
        this.mMoviesDataRepository = mMoviesDataRepository;
    }

    public Observable<String> movieDetail(String url) {
        return mMoviesDataRepository.movieDetail(url);
    }

}
