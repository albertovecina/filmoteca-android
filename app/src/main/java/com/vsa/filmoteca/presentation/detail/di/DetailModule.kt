package com.vsa.filmoteca.presentation.detail.di

import com.vsa.filmoteca.presentation.detail.DetailPresenter
import com.vsa.filmoteca.presentation.detail.DetailPresenterImpl
import com.vsa.filmoteca.view.DetailView
import com.vsa.filmoteca.view.activity.DetailActivity
import dagger.Module
import dagger.Provides

@Module
class DetailModule {

    @Provides
    fun provideDetailView(detailActivity: DetailActivity): DetailView = detailActivity

    @Provides
    fun providesDetailPresenter(detailPresenter: DetailPresenterImpl): DetailPresenter = detailPresenter

}