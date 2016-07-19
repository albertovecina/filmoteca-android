package com.vsa.filmoteca.data.usecase;

import com.vsa.filmoteca.data.repository.MoviesRepository;

import rx.Observable;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

public class GetMovieDetailUseCase {

    private MoviesRepository mMoviesRepository;

    public GetMovieDetailUseCase(MoviesRepository mMoviesRepository) {
        this.mMoviesRepository = mMoviesRepository;
    }

    public Observable<String> movieDetail(String url) {
        return mMoviesRepository.movieDetail(url);
    }

}
