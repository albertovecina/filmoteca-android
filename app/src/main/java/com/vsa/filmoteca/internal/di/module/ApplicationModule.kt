package com.vsa.filmoteca.internal.di.module

import android.app.Application
import android.content.Context
import com.vsa.filmoteca.data.source.database.MoviesDataBaseSource
import com.vsa.filmoteca.data.source.repository.MoviesPersistanceRepository
import com.vsa.filmoteca.data.source.sharedpreferences.SharedPreferencesManager
import com.vsa.filmoteca.internal.di.scope.PerApplication
import com.vsa.filmoteca.presentation.utils.tracker.FirebaseTracker
import com.vsa.filmoteca.presentation.utils.tracker.Tracker
import dagger.Module
import dagger.Provides

/**
 * Created by albertovecinasanchez on 18/7/16.
 */


@Module
class ApplicationModule {

    @Provides
    @PerApplication
    fun provideApplicationContext(application: Application): Context = application

    @Provides
    @PerApplication
    fun provideTracker(tracker: FirebaseTracker): Tracker = tracker

    @Provides
    @PerApplication
    fun provideSharedPreferencesManager(context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Provides
    @PerApplication
    fun provideMoviesDataBaseSource(context: Context): MoviesDataBaseSource {
        return MoviesDataBaseSource(context)
    }

    @Provides
    @PerApplication
    fun provideMoviesPersistanceRepository(moviesDataBaseSource: MoviesDataBaseSource, sharedPreferencesManager: SharedPreferencesManager): MoviesPersistanceRepository {
        return MoviesPersistanceRepository(moviesDataBaseSource, sharedPreferencesManager)
    }

}
