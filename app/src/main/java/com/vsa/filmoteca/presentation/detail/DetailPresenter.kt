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
class DetailPresenter @Inject constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) : Presenter<DetailView>, Observer<String> {

    private var mContentUrl: String? = null
    private var mTitle: String? = null
    private var mView: DetailView? = null

    fun onCreate(url: String, movieTitle: String) {
        if (!StringUtils.isEmpty(url)) {
            mTitle = movieTitle
            mContentUrl = url
            mView!!.setWebViewContent("<html></html>", url)
            mView!!.showMovieTitle(mTitle)
            loadContent(url)
        }
    }

    fun onDestroy() {}

    override fun setView(detailView: DetailView) {
        mView = detailView
    }

    fun onShareButtonClick() {
        mView!!.showShareDialog()
    }

    fun onShowInBrowserButtonClick() {
        mView!!.launchBrowser(mContentUrl)
    }

    fun onFilmAffinitySearchButtonClick() {
        launchFilmaffinitySearch()
    }

    fun onAboutUsButtonClick() {
        mView!!.showAboutUs()
    }

    fun onFabClick() {
        if (ConnectivityUtils.isInternetAvailable())
            mView!!.navigateToComments(mTitle)
        else
            mView!!.showErrorNoInternet()
    }

    fun loadContent(url: String) {
        mView!!.stopRefreshing()
        mView!!.hideContent()
        mView!!.showProgressDialog()
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
        mView!!.launchBrowser(url)
    }

    override fun onCompleted() {
        mView!!.hideProgressDialog()
    }

    override fun onError(e: Throwable) {
        mView!!.showTimeOutDialog()
        //Probably this error comes from an inconsistent widget data. We must to update
        //the widget information to match the entries for the next time.
        mView!!.updateWidget()
    }

    override fun onNext(html: String) {
        if (StringUtils.isEmpty(html))
            mView!!.showTimeOutDialog()
        else
            mView!!.setWebViewContent(html, mContentUrl)
        mView!!.showContent()
    }

}

