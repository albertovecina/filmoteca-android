package com.vsa.filmoteca.internal.di.module

import android.content.Context
import com.vsa.filmoteca.presentation.detail.DetailPresenter
import com.vsa.filmoteca.presentation.detail.DetailPresenterImpl
import com.vsa.filmoteca.presentation.movieslist.MoviesListPresenter
import com.vsa.filmoteca.presentation.movieslist.MoviesListPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by Alberto Vecina Sánchez on 2019-05-02.
 */
@Module
class ActivityModule(private val context: Context) {

    @Provides
    fun providesContext(): Context = context

    @Provides
    fun providesMoviesListPresenter(moviesListPresenter: MoviesListPresenterImpl): MoviesListPresenter = moviesListPresenter

    @Provides
    fun providesDetailPresenter(detailPresenter: DetailPresenterImpl): DetailPresenter = detailPresenter

}