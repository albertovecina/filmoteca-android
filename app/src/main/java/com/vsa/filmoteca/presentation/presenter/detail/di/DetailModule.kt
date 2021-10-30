package com.vsa.filmoteca.presentation.presenter.detail.di

import android.app.Activity
import com.vsa.filmoteca.presentation.presenter.detail.DetailPresenter
import com.vsa.filmoteca.presentation.presenter.detail.DetailPresenterImpl
import com.vsa.filmoteca.presentation.view.DetailView
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class DetailModule {

    @Provides
    fun providesDetailView(activity: Activity): DetailView = activity as DetailView

    @Module
    @InstallIn(ActivityComponent::class)
    interface Bindings {

        @Binds
        fun bindsDetailPresenter(detailPresenter: DetailPresenterImpl): DetailPresenter

    }

}