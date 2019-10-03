package com.vsa.filmoteca.presentation.movieslist

import com.vsa.filmoteca.presentation.Presenter
import com.vsa.filmoteca.view.MoviesListView

/**
 * Created by Alberto Vecina Sánchez on 2019-05-08.
 */
abstract class MoviesListPresenter : Presenter<MoviesListView>() {

    abstract fun onCreate(url: String? = null, title: String? = null, date: String? = null)

    abstract fun onRefreshButtonClick()

    abstract fun onNewMoviesAdded()

    abstract fun onAboutUsButtonClick()

    abstract fun onMovieRowClick(position: Int)

}