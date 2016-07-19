package com.vsa.filmoteca.internal.di.module;

import com.vsa.filmoteca.data.repository.MoviesRepository;
import com.vsa.filmoteca.internal.di.PerWidget;
import com.vsa.filmoteca.data.usecase.GetMoviesListUseCase;
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
    public GetMoviesListUseCase provideEventsListUseCase(MoviesRepository moviesRepository) {
        return new GetMoviesListUseCase(moviesRepository);
    }

    @Provides
    @PerWidget
    public EventsWidgetPresenter provideEventsWidgetPresenter(GetMoviesListUseCase getMoviesListUseCase) {
        return new EventsWidgetPresenter(getMoviesListUseCase);
    }
}
