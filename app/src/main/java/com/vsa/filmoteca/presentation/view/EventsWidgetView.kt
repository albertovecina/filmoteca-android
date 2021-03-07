package com.vsa.filmoteca.presentation.view

/**
 * Created by seldon on 15/03/15.
 */
interface EventsWidgetView {

    companion object {
        const val ACTION_WIDGET_LEFT = "action_widget_left"
        const val ACTION_WIDGET_RIGHT = "action_widget_right"
        const val ACTION_WIDGET_REFRESH = "action_widget_update"
    }

    fun setupLRButtons()

    fun setupMovieView(url: String, title: String, date: String)

    fun setupIndexView(current: Int, size: Int)

    fun refreshViews()

    fun showProgress()

    fun hideProgress()

    fun showRefreshButton()

}
