package com.vsa.filmoteca.presentation.presenter.movieslist.di

import android.app.Activity
import com.vsa.filmoteca.presentation.presenter.movieslist.MoviesListPresenter
import com.vsa.filmoteca.presentation.presenter.movieslist.MoviesListPresenterImpl
import com.vsa.filmoteca.presentation.utils.review.ReviewManager
import com.vsa.filmoteca.presentation.utils.review.ReviewManagerImpl
import com.vsa.filmoteca.presentation.view.MoviesListView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class MoviesListModule {

    @Provides
    fun providesMoviesListView(activity: Activity): MoviesListView = activity as MoviesListView

    @Provides
    fun providesRateManager(rateManager: ReviewManagerImpl): ReviewManager = rateManager

    @Provides
    fun providesMoviesListPresenter(moviesListPresenter: MoviesListPresenterImpl): MoviesListPresenter = moviesListPresenter

}