package com.vsa.filmoteca.presentation.presenter.detail

/**
 * Created by Alberto Vecina Sánchez on 2019-05-08.
 */
interface DetailPresenter {

    fun onCreate(url: String, movieTitle: String)
    
    fun onShareButtonClick()

    fun onShowInBrowserButtonClick()

    fun onFilmAffinitySearchButtonClick()

    fun onRefresh()

    fun onAboutUsButtonClick()

    fun onFabClick()

}