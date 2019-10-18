package com.vsa.filmoteca.view

/**
 * Created by seldon on 15/03/15.
 */
interface EventsWidgetView {

    fun initWidget()

    fun setupLRButtons()

    fun setupMovieView(url: String, title: String, date: String)

    fun setupIndexView(current: Int, size: Int)

    fun refreshViews()

    fun showProgress()

    fun hideProgress()

    fun showRefreshButton()

}
