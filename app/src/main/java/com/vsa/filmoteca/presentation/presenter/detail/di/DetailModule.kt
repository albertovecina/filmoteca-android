package com.vsa.filmoteca.presentation.presenter.detail.di

import com.vsa.filmoteca.presentation.presenter.detail.DetailPresenter
import com.vsa.filmoteca.presentation.presenter.detail.DetailPresenterImpl
import com.vsa.filmoteca.presentation.view.activity.DetailActivity
import com.vsa.filmoteca.view.DetailView
import dagger.Module
import dagger.Provides

@Module
class DetailModule {

    @Provides
    fun provideDetailView(detailActivity: DetailActivity): DetailView = detailActivity

    @Provides
    fun providesDetailPresenter(detailPresenter: DetailPresenterImpl): DetailPresenter = detailPresenter

}