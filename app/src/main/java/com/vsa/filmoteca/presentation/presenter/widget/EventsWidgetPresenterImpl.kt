package com.vsa.filmoteca.presentation.presenter.widget

import com.vsa.filmoteca.domain.model.Movie
import com.vsa.filmoteca.domain.usecase.GetMoviesListUseCase
import com.vsa.filmoteca.domain.usecase.MoviesPersistanceUseCase
import com.vsa.filmoteca.presentation.view.EventsWidgetView
import javax.inject.Inject

/**
 * Created by seldon on 27/03/15.
 */
class EventsWidgetPresenterImpl @Inject constructor(
    private val view: EventsWidgetView,
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val moviesPersistanceUseCase: MoviesPersistanceUseCase
) : EventsWidgetPresenter {

    private var currentMovieIndex = 0
    private var moviesListSize = 0

    override fun onRefresh() {
        view.showProgress()
        view.refreshViews()
        requestMoviesList()
    }

    override fun onButtonLeftClick() {
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
            view.setupLRButtons()
            view.setupMovieView(movie.url, movie.title, movie.date)
            view.setupIndexView(currentMovieIndex + 1, moviesListSize)
            view.refreshViews()
        }
    }

    override fun onButtonRightClick() {
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
            view.setupLRButtons()
            view.setupMovieView(movie.url, movie.title, movie.date)
            view.setupIndexView(currentMovieIndex + 1, moviesListSize)
            view.refreshViews()
        }
    }

    private fun requestMoviesList() {
        getMoviesListUseCase.moviesList {
            it.fold({ movies ->
                if (movies.isEmpty()) {
                    view.showRefreshButton()
                } else {
                    if (movies.isNotEmpty()) {
                        //Actualizando base de datos
                        moviesPersistanceUseCase.setMovies(movies)

                        //Estableciendo elemento actual y tamaño de la base de datos
                        currentMovieIndex = 0
                        moviesListSize = movies.size
                        moviesPersistanceUseCase.currentMovieIndex = 0
                        moviesPersistanceUseCase.moviesCount = moviesListSize

                        //Configurando la vista
                        view.setupLRButtons()
                        var currentMovie: Movie? = null
                        if (movies.isNotEmpty()) {
                            currentMovie = movies[currentMovieIndex]
                            view.setupMovieView(
                                currentMovie.url,
                                currentMovie.title,
                                currentMovie.date
                            )
                            view.setupIndexView(currentMovieIndex + 1, moviesListSize)
                        }
                        view.hideProgress()
                    }
                }
                view.refreshViews()
            }, {
                view.showRefreshButton()
                view.refreshViews()
            })
        }
    }

}
