package com.vsa.filmoteca.view

import com.vsa.filmoteca.view.adapter.EventDataProvider
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener

/**
 * Created by seldon on 10/03/15.
 */
interface MoviesListView {

    fun showChangeLog()

    fun showProgressDialog()

    fun hideProgressDialog()

    fun stopRefreshing()

    fun showTitle(moviesCount: Int)

    fun showWifiRequestDialog(okCancelDialogListener: OkCancelDialogListener)

    fun showWifiSettings()

    fun showTimeOutDialog()

    fun showNoEventsDialog()

    fun navigateToDetail(url: String, title: String, date: String)

    fun showAboutUs()

    fun setMovies(dataProvider: EventDataProvider)

    fun updateWidget()

    fun finish()

}
