package com.vsa.filmoteca.presentation.presenter.widget

import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.domain.usecase.GetMoviesListUseCase
import com.vsa.filmoteca.domain.usecase.MoviesPersistanceUseCase
import com.vsa.filmoteca.presentation.view.EventsWidgetView
import rx.Observer
import javax.inject.Inject

/**
 * Created by seldon on 27/03/15.
 */
class EventsWidgetPresenterImpl @Inject constructor(
        private val getMoviesListUseCase: GetMoviesListUseCase,
        private val moviesPersistanceUseCase: MoviesPersistanceUseCase)
    : EventsWidgetPresenter,
        Observer<List<Movie>> {

    private var currentMovieIndex = 0
    private var moviesListSize = 0
    private var movies: List<Movie> = ArrayList()
    override var view: EventsWidgetView? = null

    override fun onUpdate() {
        view?.initWidget()
        view?.showProgress()
        getMoviesListUseCase.moviesList().subscribe(this)
    }

    override fun onButtonLeftClick() {
        view?.initWidget()
        //Obtenemos el indice actual y el tamaño de la base de datos
        currentMovieIndex = moviesPersistanceUseCase.currentMovieIndex
        moviesListSize = moviesPersistanceUseCase.moviesCount
        if (moviesListSize != 0) {
            if (currentMovieIndex > 0) {
                currentMovieIndex--
                //Actualizamos el valor del indice
                moviesPersistanceUseCase.currentMovieIndex = currentMovieIndex
            }
            val movie = moviesPersistanceUseCase.currentMovie
            //Preparamos la vista
            view?.setupLRButtons()
            view?.setupMovieView(movie.url, movie.title, movie.date)
            view?.setupIndexView(currentMovieIndex + 1, moviesListSize)
            view?.refreshViews()
        }
    }

    override fun onButtonRightClick() {
        view?.initWidget()
        //Obtenemos el indice actual y el tamaño de la base de datos
        currentMovieIndex = moviesPersistanceUseCase.currentMovieIndex
        moviesListSize = moviesPersistanceUseCase.moviesCount
        if (moviesListSize != 0) {

            if (currentMovieIndex < moviesListSize - 1) {
                currentMovieIndex++
                //Actualizamos el valor del indice
                moviesPersistanceUseCase.currentMovieIndex = currentMovieIndex
            }
            val movie = moviesPersistanceUseCase.currentMovie
            //Preparamos la vista
            view?.setupLRButtons()
            view?.setupMovieView(movie.url, movie.title, movie.date)
            view?.setupIndexView(currentMovieIndex + 1, moviesListSize)
            view?.refreshViews()
        }
    }

    override fun onCompleted() {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    override fun onNext(movies: List<Movie>) {
        this.movies = movies
        if (this.movies.isEmpty()) {
            view?.showRefreshButton()
        } else {
            if (this.movies.isNotEmpty()) {
                //Actualizando base de datos
                moviesPersistanceUseCase.setMovies(this.movies)

                //Estableciendo elemento actual y tamaño de la base de datos
                currentMovieIndex = 0
                moviesListSize = this.movies.size
                moviesPersistanceUseCase.currentMovieIndex = 0
                moviesPersistanceUseCase.moviesCount = moviesListSize

                //Configurando la vista
                view?.setupLRButtons()
                var movie: Movie? = null
                if (this.movies.isNotEmpty())
                    movie = this.movies[currentMovieIndex]
                view?.hideProgress()
                view?.setupMovieView(movie!!.url, movie.title, movie.date)
                view?.setupIndexView(currentMovieIndex + 1, moviesListSize)
                view?.refreshViews()
            }
        }
    }
}
