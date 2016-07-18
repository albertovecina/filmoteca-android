package com.vsa.filmoteca.internal.di.module;

import android.app.Application;

import com.vsa.filmoteca.data.repository.MoviesRepository;
import com.vsa.filmoteca.data.repository.TwitterRepository;

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
    public MoviesRepository provideMoviesRepository() {
        return new MoviesRepository();
    }

    @Provides
    @Singleton
    public TwitterRepository provideTwitterRepository() {
        return new TwitterRepository();
    }

}
