package com.vsa.filmoteca.presentation.detail

import com.vsa.filmoteca.data.usecase.GetMovieDetailUseCase
import com.vsa.filmoteca.presentation.utils.ConnectivityUtils
import com.vsa.filmoteca.presentation.utils.StringUtils
import rx.Observer
import javax.inject.Inject

/**
 * Created by seldon on 13/03/15.
 */
class DetailPresenterImpl @Inject constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) : DetailPresenter(), Observer<String> {

    private var contentUrl: String? = null
    private var title: String? = null

    override fun onCreate(url: String, movieTitle: String) {
        if (!StringUtils.isEmpty(url)) {
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
        if (ConnectivityUtils.isInternetAvailable())
            view.navigateToComments(title)
        else
            view.showErrorNoInternet()
    }

    override fun onRefresh() {
        if (contentUrl != null && !contentUrl!!.isEmpty())
            loadContent(contentUrl!!)
    }

    private fun loadContent(url: String) {
        view.stopRefreshing()
        view.hideContent()
        view.showProgressDialog()
        getMovieDetailUseCase.movieDetail(url).subscribe(this)
    }

    private fun launchFilmAffinitySearch() {
        var searchString = title!!.replace(" ", "+")
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
            view.setWebViewContent(html, contentUrl)
        view.showContent()
    }

}

