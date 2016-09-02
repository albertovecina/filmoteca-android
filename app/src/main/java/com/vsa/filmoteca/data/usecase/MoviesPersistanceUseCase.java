package com.vsa.filmoteca.data.usecase;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.data.repository.MoviesPersistanceRepository;

import java.util.List;

/**
 * Created by albertovecinasanchez on 22/7/16.
 */

public class MoviesPersistanceUseCase {

    private MoviesPersistanceRepository mMoviesPersistanceRepository;

    public MoviesPersistanceUseCase(MoviesPersistanceRepository moviesPersistanceRepository) {
        mMoviesPersistanceRepository = moviesPersistanceRepository;
    }

    public int getCurrentMovieIndex() {
        return mMoviesPersistanceRepository.getCurrentMovieIndex();
    }

    public void setCurrentMovieIndex(int index) {
        mMoviesPersistanceRepository.setCurrentMovieIndex(index);
    }

    public int getMoviesCount() {
        return mMoviesPersistanceRepository.getMoviesCount();
    }

    public void setMoviesCount(int size) {
        mMoviesPersistanceRepository.setMoviesCount(size);
    }

    public Movie getCurrentMovie() {
        return mMoviesPersistanceRepository.getCurrentMovie();
    }

    public void setMovies(List<Movie> movies) {
        mMoviesPersistanceRepository.setMovies(movies);
    }

}
