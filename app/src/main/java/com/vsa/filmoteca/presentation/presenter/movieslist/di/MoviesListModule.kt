package com.vsa.filmoteca.presentation.presenter.movieslist.di

import android.app.Activity
import com.vsa.filmoteca.presentation.presenter.movieslist.MoviesListPresenter
import com.vsa.filmoteca.presentation.presenter.movieslist.MoviesListPresenterImpl
import com.vsa.filmoteca.presentation.utils.review.ReviewManager
import com.vsa.filmoteca.presentation.utils.review.ReviewManagerImpl
import com.vsa.filmoteca.presentation.view.MoviesListView
import com.vsa.filmoteca.presentation.view.activity.MoviesListActivity
import dagger.Module
import dagger.Provides

@Module
class MoviesListModule {

    @Provides
    fun providesMoviesListActivity(moviesListActivity: MoviesListActivity): Activity = moviesListActivity

    @Provides
    fun provideMoviesListView(moviesListActivity: MoviesListActivity): MoviesListView = moviesListActivity

    @Provides
    fun provideRateManager(rateManager: ReviewManagerImpl): ReviewManager = rateManager

    @Provides
    fun providesMoviesListPresenter(moviesListPresenter: MoviesListPresenterImpl): MoviesListPresenter = moviesListPresenter

}