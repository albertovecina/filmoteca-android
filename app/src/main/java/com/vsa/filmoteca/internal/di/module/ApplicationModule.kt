package com.vsa.filmoteca.internal.di.module

import android.app.Application
import android.content.Context
import com.vsa.filmoteca.data.repository.MoviesPersistanceRepository
import com.vsa.filmoteca.data.repository.database.MoviesDataBaseSource
import com.vsa.filmoteca.data.repository.sharedpreferences.SharedPreferencesManager
import com.vsa.filmoteca.internal.di.scope.PerApplication
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
