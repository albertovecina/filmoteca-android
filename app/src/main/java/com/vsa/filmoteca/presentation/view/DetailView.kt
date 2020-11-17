package com.vsa.filmoteca.presentation.view

/**
 * Created by seldon on 13/03/15.
 */
interface DetailView {

    fun hideContent()

    fun showContent()

    fun showProgressDialog()

    fun hideProgressDialog()

    fun showMovieTitle(title: String)

    fun updateWidget()

    fun stopRefreshing()

    fun showTimeOutDialog()

    fun launchBrowser(url: String)

    fun showErrorNoInternet()

    fun showShareDialog()

    fun showAboutUs()

    fun setWebViewContent(html: String, baseUrl: String)

    fun navigateToComments(title: String)

}
