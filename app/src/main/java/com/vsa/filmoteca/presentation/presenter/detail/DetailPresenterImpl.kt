package com.vsa.filmoteca.presentation.presenter.detail

import com.vsa.filmoteca.domain.usecase.GetMovieDetailUseCase
import com.vsa.filmoteca.presentation.utils.StringUtils
import com.vsa.filmoteca.presentation.view.DetailView
import rx.Observer
import javax.inject.Inject

/**
 * Created by seldon on 13/03/15.
 */
class DetailPresenterImpl @Inject constructor(
        private val view: DetailView,
        private val getMovieDetailUseCase: GetMovieDetailUseCase)
    : DetailPresenter, Observer<String> {

    private lateinit var contentUrl: String
    private lateinit var title: String

    override fun onCreate(url: String, movieTitle: String) {
        if (url.isNotEmpty()) {
            title = movieTitle
            contentUrl = url
            view.setWebViewContent("<html></html>", url)
            view.showMovieTitle(title)
            loadContent(url)
        }
    }

    override fun onDestroy() {}

    override fun onShareButtonClick() {
        view.showShareDialog()
    }

    override fun onShowInBrowserButtonClick() {
        view.launchBrowser(contentUrl)
    }

    override fun onFilmAffinitySearchButtonClick() {
        launchFilmAffinitySearch()
    }

    override fun onAboutUsButtonClick() {
        view.showAboutUs()
    }

    override fun onFabClick() {
        if (getMovieDetailUseCase.isInternetAvailable())
            view.navigateToComments(title)
        else
            view.showErrorNoInternet()
    }

    override fun onRefresh() {
        if (contentUrl.isNotEmpty())
            loadContent(contentUrl)
    }

    private fun loadContent(url: String) {
        view.stopRefreshing()
        view.hideContent()
        view.showProgressDialog()
        getMovieDetailUseCase.movieDetail(url).subscribe(this)
    }

    private fun launchFilmAffinitySearch() {
        var searchString = title.replace(" ", "+")
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
        if (html.isEmpty())
            view.showTimeOutDialog()
        else
            view.setWebViewContent(html, contentUrl)
        view.showContent()
    }

}

