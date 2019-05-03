package com.vsa.filmoteca.internal.di.module;

import android.app.Application;

import com.vsa.filmoteca.data.repository.TwitterDataRepository;
import com.vsa.filmoteca.data.repository.MoviesPersistanceRepository;
import com.vsa.filmoteca.data.repository.database.MoviesDataBaseSource;
import com.vsa.filmoteca.data.repository.sharedpreferences.SharedPreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */


@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public SharedPreferencesManager provideSharedPreferencesManager() {
        return new SharedPreferencesManager(mApplication);
    }

    @Provides
    @Singleton
    public MoviesDataBaseSource provideMoviesDataBaseSource() {
        return new MoviesDataBaseSource(mApplication);
    }

    @Provides
    @Singleton
    public TwitterDataRepository provideTwitterRepository() {
        return new TwitterDataRepository();
    }

    @Provides
    @Singleton
    public MoviesPersistanceRepository provideMoviesPersistanceRepository(MoviesDataBaseSource moviesDataBaseSource, SharedPreferencesManager sharedPreferencesManager) {
        return new MoviesPersistanceRepository(moviesDataBaseSource, sharedPreferencesManager);
    }

}
