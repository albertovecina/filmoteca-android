package com.vsa.filmoteca.presentation.presenter.movieslist.di

import android.app.Activity
import com.vsa.filmoteca.presentation.presenter.movieslist.MoviesListPresenter
import com.vsa.filmoteca.presentation.presenter.movieslist.MoviesListPresenterImpl
import com.vsa.filmoteca.presentation.utils.review.ReviewManager
import com.vsa.filmoteca.presentation.utils.review.ReviewManagerImpl
import com.vsa.filmoteca.presentation.view.MoviesListView
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class MoviesListModule {

    @Provides
    fun providesMoviesListView(activity: Activity): MoviesListView = activity as MoviesListView

    @Module
    @InstallIn(ActivityComponent::class)
    interface Bindings {

        @Binds
        fun bindsRateManager(rateManager: ReviewManagerImpl): ReviewManager

        @Binds
        fun bindsMoviesListPresenter(moviesListPresenter: MoviesListPresenterImpl): MoviesListPresenter

    }

}