package com.vsa.filmoteca.presentation.movieslist

/**
 * Created by Alberto Vecina Sánchez on 2019-05-08.
 */
interface MoviesListPresenter {

    fun onCreate(url: String? = null, title: String? = null, date: String? = null)

    fun onRefreshButtonClick()

    fun onNewMoviesAdded()

    fun onAboutUsButtonClick()

    fun onMovieRowClick(position: Int)

}