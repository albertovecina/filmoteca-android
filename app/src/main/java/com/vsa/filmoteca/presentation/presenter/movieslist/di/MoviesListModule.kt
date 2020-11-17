package com.vsa.filmoteca.presentation.presenter.movieslist.di

import com.vsa.filmoteca.presentation.presenter.movieslist.MoviesListPresenter
import com.vsa.filmoteca.presentation.presenter.movieslist.MoviesListPresenterImpl
import com.vsa.filmoteca.presentation.view.MoviesListView
import com.vsa.filmoteca.presentation.view.activity.MoviesListActivity
import dagger.Module
import dagger.Provides

@Module
class MoviesListModule {

    @Provides
    fun provideMoviesListView(moviesListActivity: MoviesListActivity): MoviesListView = moviesListActivity

    @Provides
    fun providesMoviesListPresenter(moviesListPresenter: MoviesListPresenterImpl): MoviesListPresenter = moviesListPresenter

}