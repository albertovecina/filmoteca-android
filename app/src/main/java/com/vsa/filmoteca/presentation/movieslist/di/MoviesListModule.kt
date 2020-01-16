package com.vsa.filmoteca.presentation.movieslist.di

import com.vsa.filmoteca.presentation.movieslist.MoviesListPresenter
import com.vsa.filmoteca.presentation.movieslist.MoviesListPresenterImpl
import com.vsa.filmoteca.view.MoviesListView
import com.vsa.filmoteca.view.activity.MoviesListActivity
import dagger.Module
import dagger.Provides

@Module
class MoviesListModule {

    @Provides
    fun provideMoviesListView(moviesListActivity: MoviesListActivity): MoviesListView = moviesListActivity

    @Provides
    fun providesMoviesListPresenter(moviesListPresenter: MoviesListPresenterImpl): MoviesListPresenter = moviesListPresenter

}