package com.vsa.filmoteca.data.usecase;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.data.repository.MoviesDataRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

public class GetMoviesListUseCase {

    private MoviesDataRepository mMoviesDataRepository;

    public GetMoviesListUseCase(MoviesDataRepository moviesDataRepository) {
        mMoviesDataRepository = moviesDataRepository;
    }

    public Observable<List<Movie>> moviesList() {
        return mMoviesDataRepository.moviesList();
    }

}
