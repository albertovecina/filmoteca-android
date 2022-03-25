package com.vsa.filmoteca.presentation.view

import com.vsa.filmoteca.core.presentation.view.LoadingView

/**
 * Created by seldon on 13/03/15.
 */
interface DetailView : LoadingView {

    fun hideContent()

    fun showContent()

    fun showMovieTitle(title: String)

    fun updateWidget()

    fun stopRefreshing()

    fun showTimeOutDialog()

    fun launchBrowser(url: String)

    fun showErrorNoInternet()

    fun showShareDialog()

    fun showAboutUs()

    fun setWebViewContent(html: String, baseUrl: String)

}
