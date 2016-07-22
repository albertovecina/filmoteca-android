package com.vsa.filmoteca.internal.di.module;

import com.vsa.filmoteca.data.repository.MoviesDataRepository;
import com.vsa.filmoteca.data.repository.MoviesPersistanceRepository;
import com.vsa.filmoteca.data.usecase.GetMoviesListUseCase;
import com.vsa.filmoteca.data.usecase.MoviesPersistanceUseCase;
import com.vsa.filmoteca.internal.di.PerWidget;
import com.vsa.filmoteca.presentation.widget.EventsWidgetPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
public class MoviesWidgetModule {

    @Provides
    @PerWidget
    public GetMoviesListUseCase provideGetMoviesListUseCase(MoviesDataRepository moviesDataRepository) {
        return new GetMoviesListUseCase(moviesDataRepository);
    }

    @Provides
    @PerWidget
    public MoviesPersistanceUseCase provideMoviesPersistanceUseCase(MoviesPersistanceRepository moviesPersistanceRepository) {
        return new MoviesPersistanceUseCase(moviesPersistanceRepository);
    }

    @Provides
    @PerWidget
    public EventsWidgetPresenter provideEventsWidgetPresenter(GetMoviesListUseCase getMoviesListUseCase, MoviesPersistanceUseCase moviesPersistanceUseCase) {
        return new EventsWidgetPresenter(getMoviesListUseCase, moviesPersistanceUseCase);
    }
}
