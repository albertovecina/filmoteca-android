package com.vsa.filmoteca.presentation.detail

import com.vsa.filmoteca.data.usecase.GetMovieDetailUseCase
import com.vsa.filmoteca.presentation.Presenter
import com.vsa.filmoteca.presentation.utils.ConnectivityUtils
import com.vsa.filmoteca.presentation.utils.StringUtils
import com.vsa.filmoteca.view.DetailView

import rx.Observer
import javax.inject.Inject

/**
 * Created by seldon on 13/03/15.
 */
class DetailPresenterImpl @Inject constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) : Presenter<DetailView>(), Observer<String> {

    private var mContentUrl: String? = null
    private var mTitle: String? = null

    fun onCreate(url: String, movieTitle: String) {
        if (!StringUtils.isEmpty(url)) {
            mTitle = movieTitle
            mContentUrl = url
            view.setWebViewContent("<html></html>", url)
            view.showMovieTitle(mTitle)
            loadContent(url)
        }
    }

    fun onDestroy() {}

    fun onShareButtonClick() {
        view.showShareDialog()
    }

    fun onShowInBrowserButtonClick() {
        view.launchBrowser(mContentUrl)
    }

    fun onFilmAffinitySearchButtonClick() {
        launchFilmaffinitySearch()
    }

    fun onAboutUsButtonClick() {
        view.showAboutUs()
    }

    fun onFabClick() {
        if (ConnectivityUtils.isInternetAvailable())
            view.navigateToComments(mTitle)
        else
            view.showErrorNoInternet()
    }

    fun loadContent(url: String) {
        view.stopRefreshing()
        view.hideContent()
        view.showProgressDialog()
        getMovieDetailUseCase.movieDetail(url).subscribe(this)
    }

    fun onRefresh() {
        if (mContentUrl != null && !mContentUrl!!.isEmpty())
            loadContent(mContentUrl!!)
    }

    private fun clearContent() {

    }

    private fun launchFilmaffinitySearch() {
        var searchString = mTitle!!.replace(" ", "+")
        searchString = StringUtils.removeAccents(searchString)
        val url = "http://m.filmaffinity.com/es/search.php?stext=$searchString&stype=title"
        view.launchBrowser(url)
    }

    override fun onCompleted() {
        view.hideProgressDialog()
    }

    override fun onError(e: Throwable) {
        view.showTimeOutDialog()
        //Probably this error comes from an inconsistent widget data. We must to update
        //the widget information to match the entries for the next time.
        view.updateWidget()
    }

    override fun onNext(html: String) {
        if (StringUtils.isEmpty(html))
            view.showTimeOutDialog()
        else
            view.setWebViewContent(html, mContentUrl)
        view.showContent()
    }

}

