package com.vsa.filmoteca.presentation.presenter.detail.di

import android.content.Context
import com.vsa.filmoteca.presentation.presenter.detail.DetailPresenter
import com.vsa.filmoteca.presentation.presenter.detail.DetailPresenterImpl
import com.vsa.filmoteca.presentation.view.DetailView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class DetailModule {

    @Provides
    fun providesDetailView(@ActivityContext detailActivity: Context): DetailView = detailActivity as DetailView

    @Provides
    fun providesDetailPresenter(detailPresenter: DetailPresenterImpl): DetailPresenter = detailPresenter

}