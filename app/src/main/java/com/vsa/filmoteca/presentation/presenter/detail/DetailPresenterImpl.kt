package com.vsa.filmoteca.presentation.presenter.detail

import com.vsa.filmoteca.domain.usecase.GetMovieDetailUseCase
import com.vsa.filmoteca.presentation.tracker.Tracker
import com.vsa.filmoteca.core.extensions.toUrlEncoded
import com.vsa.filmoteca.core.extensions.weak
import com.vsa.filmoteca.presentation.view.DetailView
import javax.inject.Inject

/**
 * Created by seldon on 13/03/15.
 */
class DetailPresenterImpl @Inject constructor(
    view: DetailView,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val tracker: Tracker
) : DetailPresenter {

    private val view: DetailView? by weak(view)

    private var contentUrl: String = ""
    private var title: String = ""

    override fun onCreate(url: String, movieTitle: String) {
        if (url.isNotEmpty()) {
            title = movieTitle
            contentUrl = url
            view?.setWebViewContent("<html></html>", url)
            view?.showMovieTitle(title)
            loadContent(url)
        }
    }

    override fun onShareButtonClick() {
        tracker.logClickShareMovie(title)
        view?.showShareDialog()
    }

    override fun onShowInBrowserButtonClick() {
        tracker.logClickWebsite(title)
        view?.launchBrowser(contentUrl)
    }

    override fun onFilmAffinitySearchButtonClick() {
        tracker.logClickFilmAffinity(title)
        launchFilmAffinitySearch()
    }

    override fun onAboutUsButtonClick() {
        view?.showAboutUs()
    }

    override fun onFabClick() {
        if (getMovieDetailUseCase.isInternetAvailable())
            view?.navigateToComments(title)
        else
            view?.showErrorNoInternet()
    }

    override fun onRefresh() {
        if (contentUrl.isNotEmpty())
            loadContent(contentUrl)
    }

    private fun loadContent(url: String) {
        view?.stopRefreshing()
        view?.hideContent()
        view?.showLoading()

        getMovieDetailUseCase.movieDetail(url) {
            view?.hideLoading()
            it.fold({ html ->
                view?.setWebViewContent(html, contentUrl)
                view?.showContent()
            }, {
                view?.showTimeOutDialog()
                //Probably this error comes from an inconsistent widget data. We must to update
                //the widget information to match the entries for the next time.
                view?.updateWidget()
            })
        }
    }

    private fun launchFilmAffinitySearch() {
        val url =
            "http://m.filmaffinity.com/es/search.php?stext=${title.toUrlEncoded()}&stype=title"
        view?.launchBrowser(url)
    }

}

