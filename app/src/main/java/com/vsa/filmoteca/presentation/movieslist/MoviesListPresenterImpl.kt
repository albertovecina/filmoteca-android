package com.vsa.filmoteca.presentation.movieslist

import com.vsa.filmoteca.data.domain.Movie
import com.vsa.filmoteca.data.usecase.ClearCacheUseCase
import com.vsa.filmoteca.data.usecase.GetMoviesListUseCase
import com.vsa.filmoteca.view.adapter.EventDataProvider
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener
import rx.Observer
import javax.inject.Inject

/**
 * Created by seldon on 10/03/15.
 */

class MoviesListPresenterImpl
@Inject constructor(private val clearCacheUseCase: ClearCacheUseCase,
                    private val getMoviesListUseCase: GetMoviesListUseCase) :
        MoviesListPresenter(), OkCancelDialogListener, EventDataProvider, Observer<List<Movie>> {

    private var moviesList: List<Movie> = ArrayList()

    override fun onCreate(url: String?, title: String?, date: String?) {
        clearCacheUseCase.clearExpiredCacheFiles()
        loadMovies()
        if (url != null)
            view.navigateToDetail(url, title ?: "", date ?: "")
    }

    override fun onRefreshMenuButtonClick() {
        loadMovies()
    }

    override fun onRefresh() {
        loadMovies()
    }

    override fun onAboutUsButtonClick() {
        view.showAboutUs()
    }

    override fun onMovieRowClick(position: Int) {
        val movie = moviesList[position]
        view.navigateToDetail(movie.url, movie.title, movie.date)
    }

    private fun loadMovies() {
        view.stopRefreshing()
        view.showProgressDialog()
        getMoviesListUseCase.moviesList().subscribe(this)
    }

    override fun onAcceptButtonPressed() {
        view.showWifiSettings()
    }

    override fun onCancelButtonPressed() {
        view.finish()
    }

    override fun onCompleted() {
        view.hideProgressDialog()
    }

    override fun onError(e: Throwable) {
        view.showTimeOutDialog()
    }

    override fun onNext(movies: List<Movie>?) {
        if (movies != null)
            moviesList = movies
        view.showTitle(moviesList.size)
        if (moviesList.isEmpty())
            view.showNoEventsDialog()
        else
            view.setMovies(this)
        view.showChangeLog()
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
