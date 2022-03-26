package com.vsa.filmoteca.presentation.presenter.movieslist

import com.vsa.filmoteca.core.extensions.weak
import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.domain.usecase.ClearCacheUseCase
import com.vsa.filmoteca.domain.usecase.GetMoviesListUseCase
import com.vsa.filmoteca.presentation.tracker.Tracker
import com.vsa.filmoteca.presentation.utils.review.ReviewManager
import com.vsa.filmoteca.presentation.view.MoviesListView
import com.vsa.filmoteca.presentation.view.adapter.model.toViewModel
import com.vsa.filmoteca.presentation.view.dialog.interfaces.OkCancelDialogListener
import javax.inject.Inject

/**
 * Created by seldon on 10/03/15.
 */

class MoviesListPresenterImpl
@Inject constructor(
    view: MoviesListView,
    private val clearCacheUseCase: ClearCacheUseCase,
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val reviewManager: ReviewManager,
    private val tracker: Tracker
) : MoviesListPresenter, OkCancelDialogListener {

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
        tracker.logClickMovieItem(movie.title)
        view?.navigateToDetail(movie.url, movie.title, movie.date)
    }

    private fun loadMovies() {
        view?.stopRefreshing()
        view?.showLoading()
        getMoviesListUseCase.moviesList {
            it.fold({ movies ->
                moviesList = movies
                view?.showTitle(moviesList.size)
                if (moviesList.isEmpty())
                    view?.showNoEventsDialog()
                else
                    view?.setMovies(movies.toViewModel())
            }, {
                view?.showTimeOutDialog()
            })
            view?.hideLoading()
        }
    }

    override fun onAcceptButtonPressed() {
        view?.showWifiSettings()
    }

    override fun onCancelButtonPressed() {
        view?.finish()
    }

}
