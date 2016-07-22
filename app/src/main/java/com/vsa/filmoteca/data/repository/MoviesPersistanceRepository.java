package com.vsa.filmoteca.data.repository;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.data.repository.database.MoviesDataBaseSource;
import com.vsa.filmoteca.data.repository.sharedpreferences.SharedPreferencesManager;

import java.util.List;

/**
 * Created by albertovecinasanchez on 22/7/16.
 */

public class MoviesPersistanceRepository {

    private MoviesDataBaseSource mMoviesDataBaseSource;
    private SharedPreferencesManager mSharedPreferencesManager;

    public MoviesPersistanceRepository(MoviesDataBaseSource moviesDataBaseSource, SharedPreferencesManager sharedPreferencesManager) {
        mMoviesDataBaseSource = moviesDataBaseSource;
        mSharedPreferencesManager = sharedPreferencesManager;
    }

    public int getCurrentMovieIndex() {
        return mSharedPreferencesManager.getCurrentMovieIndex();
    }

    public void setCurrentMovieIndex(int index) {
        mSharedPreferencesManager.setCurrentMovieIndex(index);
    }

    public int getMoviesCount() {
        return mSharedPreferencesManager.getMoviesCount();
    }

    public void setMoviesCount(int size) {
        mSharedPreferencesManager.setMoviesCount(size);
    }

    public Movie getCurrentMovie() {
        mMoviesDataBaseSource.open();
        Movie movie = mMoviesDataBaseSource.getMovie(getCurrentMovieIndex());
        mMoviesDataBaseSource.close();
        return movie;
    }

    public void setMovies(List<Movie> movies) {
        mMoviesDataBaseSource.open();
        mMoviesDataBaseSource.clearMovies();
        for (int x = 0; x < movies.size(); x++)
            mMoviesDataBaseSource.insertMovie(x, movies.get(x));
        mMoviesDataBaseSource.close();
    }

}
