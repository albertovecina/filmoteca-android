package com.vsa.filmoteca.internal.di.module;

import com.vsa.filmoteca.data.repository.CacheRepository;
import com.vsa.filmoteca.data.repository.MoviesRepository;
import com.vsa.filmoteca.data.repository.TwitterRepository;
import com.vsa.filmoteca.data.repository.ws.CommentsTwitterClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by albertovecinasanchez on 8/3/16.
 */
@Module
public class MainModule {

    @Provides
    @Singleton
    CacheRepository provideCacheRepository() {
        return new CacheRepository();
    }

    @Provides
    @Singleton
    MoviesRepository provideMoviesRepository() {
        return new MoviesRepository();
    }

    @Provides
    @Singleton
    TwitterRepository provideTwitterRepository() {
        return new TwitterRepository();
    }

}
