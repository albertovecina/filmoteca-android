package com.vsa.filmoteca.presentation.usecase;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.data.repository.MoviesRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

public class GetMoviesListUseCase {

    private MoviesRepository mMoviesRepository;

    public GetMoviesListUseCase(MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
    }

    public Observable<List<Movie>> moviesList() {
        return mMoviesRepository.moviesList();
    }

}
