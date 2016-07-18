package com.vsa.filmoteca.internal.di.module;

import android.app.Application;

import com.vsa.filmoteca.data.repository.CacheRepository;
import com.vsa.filmoteca.data.repository.MoviesRepository;
import com.vsa.filmoteca.internal.di.PerActivity;
import com.vsa.filmoteca.presentation.usecase.ClearCacheUseCase;
import com.vsa.filmoteca.presentation.usecase.GetMoviesListUseCase;
import com.vsa.filmoteca.presentation.movieslist.MoviesListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
public class MoviesListModule {

    @Provides
    @PerActivity
    CacheRepository provideCacheRepository(Application application) {
        return new CacheRepository(application);
    }

    @Provides
    @PerActivity
    ClearCacheUseCase provideClearCacheUseCase(CacheRepository cacheRepository) {
        return new ClearCacheUseCase(cacheRepository);
    }

    @Provides
    @PerActivity
    GetMoviesListUseCase provideMoviesUseCase(MoviesRepository moviesRepository) {
        return new GetMoviesListUseCase(moviesRepository);
    }

    @Provides
    @PerActivity
    MoviesListPresenter provideMoviesListPresenter(ClearCacheUseCase clearCacheUseCase, GetMoviesListUseCase getMoviesListUseCase) {
        return new MoviesListPresenter(clearCacheUseCase, getMoviesListUseCase);
    }

}
