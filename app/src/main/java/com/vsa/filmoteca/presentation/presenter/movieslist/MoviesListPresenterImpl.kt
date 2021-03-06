package com.vsa.filmoteca.presentation.presenter.movieslist

import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.domain.usecase.ClearCacheUseCase
import com.vsa.filmoteca.domain.usecase.GetMoviesListUseCase
import com.vsa.filmoteca.presentation.utils.extensions.weak
import com.vsa.filmoteca.presentation.utils.review.ReviewManager
import com.vsa.filmoteca.presentation.view.MoviesListView
import com.vsa.filmoteca.presentation.view.adapter.EventDataProvider
import com.vsa.filmoteca.presentation.view.dialog.interfaces.OkCancelDialogListener
import rx.Observer
import javax.inject.Inject

/**
 * Created by seldon on 10/03/15.
 */

class MoviesListPresenterImpl
@Inject constructor(view: MoviesListView,
                    private val reviewManager: ReviewManager,
                    private val clearCacheUseCase: ClearCacheUseCase,
                    private val getMoviesListUseCase: GetMoviesListUseCase) :
        MoviesListPresenter, OkCancelDialogListener, EventDataProvider, Observer<List<Movie>> {

    private val view: MoviesListView? by weak(view)
    private var moviesList: List<Movie> = ArrayList()

    override fun onCreate(url: String?, title: String?, date: String?) {
        clearCacheUseCase.clearExpiredCacheFiles()
        reviewManager.increaseAppVisits()
        loadMovies()
        if (url == null)
            reviewManager.showRateIfNecessary()
        else
            view?.navigateToDetail(url, title ?: "", date ?: "")
    }

    override fun onNewMoviesAdded() = loadMovies()


    override fun onRefreshButtonClick() {
        view?.updateWidget()
        loadMovies()
    }

    override fun onAboutUsButtonClick() {
        view?.showAboutUs()
    }

    override fun onMovieRowClick(position: Int) {
        val movie = moviesList[position]
        view?.navigateToDetail(movie.url, movie.title, movie.date)
    }

    private fun loadMovies() {
        view?.stopRefreshing()
        view?.showLoading()
        getMoviesListUseCase.moviesList().subscribe(this)
    }

    override fun onAcceptButtonPressed() {
        view?.showWifiSettings()
    }

    override fun onCancelButtonPressed() {
        view?.finish()
    }

    override fun onCompleted() {
        view?.hideLoading()
    }

    override fun onError(e: Throwable) {
        view?.showTimeOutDialog()
    }

    override fun onNext(movies: List<Movie>?) {
        if (movies != null)
            moviesList = movies
        view?.showTitle(moviesList.size)
        if (moviesList.isEmpty())
            view?.showNoEventsDialog()
        else
            view?.setMovies(this)
        view?.showChangeLog()
    }

    override fun getTitle(index: Int): String {
        return moviesList[index].title
    }

    override fun getDate(index: Int): String {
        return moviesList[index].date
    }

    override fun getSize(): Int {
        return moviesList.size
    }
}
