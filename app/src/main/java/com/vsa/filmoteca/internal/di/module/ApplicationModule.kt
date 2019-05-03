package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.FilmotecaApplication
import com.vsa.filmoteca.data.repository.MoviesPersistanceRepository
import com.vsa.filmoteca.data.repository.TwitterDataRepository
import com.vsa.filmoteca.data.repository.database.MoviesDataBaseSource
import com.vsa.filmoteca.data.repository.sharedpreferences.SharedPreferencesManager
import com.vsa.filmoteca.internal.di.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Created by albertovecinasanchez on 18/7/16.
 */


@Module
class ApplicationModule(private val application: FilmotecaApplication) {

    @Provides
    @PerApplication
    fun provideApplication(): FilmotecaApplication {
        return application
    }

    @Provides
    @PerApplication
    fun provideSharedPreferencesManager(): SharedPreferencesManager {
        return SharedPreferencesManager(application)
    }

    @Provides
    @PerApplication
    fun provideMoviesDataBaseSource(): MoviesDataBaseSource {
        return MoviesDataBaseSource(application)
    }

    @Provides
    @PerApplication
    fun provideTwitterRepository(): TwitterDataRepository {
        return TwitterDataRepository()
    }

    @Provides
    @PerApplication
    fun provideMoviesPersistanceRepository(moviesDataBaseSource: MoviesDataBaseSource, sharedPreferencesManager: SharedPreferencesManager): MoviesPersistanceRepository {
        return MoviesPersistanceRepository(moviesDataBaseSource, sharedPreferencesManager)
    }

}
