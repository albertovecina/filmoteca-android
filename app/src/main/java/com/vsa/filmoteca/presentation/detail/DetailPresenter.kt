package com.vsa.filmoteca.presentation.detail

import com.vsa.filmoteca.presentation.Presenter
import com.vsa.filmoteca.view.DetailView

/**
 * Created by Alberto Vecina Sánchez on 2019-05-08.
 */
abstract class DetailPresenter : Presenter<DetailView>() {

    abstract fun onCreate(url: String, movieTitle: String)

    abstract fun onDestroy()

    abstract fun onShareButtonClick()

    abstract fun onShowInBrowserButtonClick()

    abstract fun onFilmAffinitySearchButtonClick()

    abstract fun onRefresh()

    abstract fun onAboutUsButtonClick()

    abstract fun onFabClick()

}