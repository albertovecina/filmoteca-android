package com.vsa.filmoteca.internal.di.module;

import com.vsa.filmoteca.data.repository.MoviesDataRepository;
import com.vsa.filmoteca.internal.di.PerActivity;
import com.vsa.filmoteca.presentation.detail.DetailPresenter;
import com.vsa.filmoteca.data.usecase.GetMovieDetailUseCase;

import dagger.Module;
import dagger.Provides;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
public class MovieDetailModule {

    @Provides
    @PerActivity
    public GetMovieDetailUseCase provideGetMovieDetailUseCase(MoviesDataRepository moviesDataRepository) {
        return new GetMovieDetailUseCase(moviesDataRepository);
    }

    @Provides
    @PerActivity
    public DetailPresenter provideDetailPresenter(GetMovieDetailUseCase getMovieDetailUseCase) {
        return new DetailPresenter(getMovieDetailUseCase);
    }

}
