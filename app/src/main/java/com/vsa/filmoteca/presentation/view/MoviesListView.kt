package com.vsa.filmoteca.presentation.view

import com.vsa.filmoteca.core.presentation.view.LoadingView
import com.vsa.filmoteca.presentation.view.adapter.model.MovieViewModel
import com.vsa.filmoteca.presentation.view.dialog.interfaces.OkCancelDialogListener

/**
 * Created by seldon on 10/03/15.
 */
interface MoviesListView : LoadingView {

    fun showChangeLog()

    fun stopRefreshing()

    fun showTitle(moviesCount: Int)

    fun showWifiRequestDialog(okCancelDialogListener: OkCancelDialogListener)

    fun showWifiSettings()

    fun showTimeOutDialog()

    fun showNoEventsDialog()

    fun navigateToDetail(url: String, title: String, date: String)

    fun showAboutUs()

    fun setMovies(movies: List<MovieViewModel>)

    fun updateWidget()

    fun finish()

}
