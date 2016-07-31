package com.vsa.filmoteca.presentation.widget;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.data.usecase.GetMoviesListUseCase;
import com.vsa.filmoteca.data.usecase.MoviesPersistanceUseCase;
import com.vsa.filmoteca.presentation.utils.Constants;
import com.vsa.filmoteca.view.EventsWidgetView;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;

/**
 * Created by seldon on 27/03/15.
 */
public class EventsWidgetPresenter implements Observer<List<Movie>> {

    private GetMoviesListUseCase mGetMoviesListUseCase;
    private MoviesPersistanceUseCase mMoviesPersistanceUseCase;
    private EventsWidgetView mView;

    private int mCurrentMovieIndex = 0;
    private int mMoviesListSize = 0;

    private List<Movie> mMovies;

    @Inject
    public EventsWidgetPresenter(GetMoviesListUseCase moviesListInteractor, MoviesPersistanceUseCase moviesPersistanceUseCase) {
        mGetMoviesListUseCase = moviesListInteractor;
        mMoviesPersistanceUseCase = moviesPersistanceUseCase;
    }

    public void setView(EventsWidgetView view) {
        mView = view;
    }

    public void onUpdate() {
        mView.initWidget();
        mView.showProgress();
        mGetMoviesListUseCase.moviesList().subscribe(this);
    }

    public void onButtonClick(String action) {
        mView.initWidget();
        if (action.equals(Constants.ACTION_WIDGET_LEFT) || action.equals(Constants.ACTION_WIDGET_RIGHT)) {
            //Obtenemos el indice actual y el tamaño de la base de datos
            mCurrentMovieIndex = mMoviesPersistanceUseCase.getCurrentMovieIndex();
            mMoviesListSize = mMoviesPersistanceUseCase.getMoviesCount();
            if (mMoviesListSize != 0) {
                if (action.equals(Constants.ACTION_WIDGET_LEFT)) {
                    if (mCurrentMovieIndex > 0) {
                        mCurrentMovieIndex--;
                        //Actualizamos el valor del indice
                        mMoviesPersistanceUseCase.setCurrentMovieIndex(mCurrentMovieIndex);
                    }
                } else if (action.equals(Constants.ACTION_WIDGET_RIGHT)) {
                    if (mCurrentMovieIndex < (mMoviesListSize - 1)) {
                        mCurrentMovieIndex++;
                        //Actualizamos el valor del indice
                        mMoviesPersistanceUseCase.setCurrentMovieIndex(mCurrentMovieIndex);
                    }
                }
                Movie movie = mMoviesPersistanceUseCase.getCurrentMovie();
                //Preparamos la vista
                mView.setupLRButtons();
                mView.setupMovieView(movie.getUrl(), movie.getTitle(), movie.getDate());
                mView.setupIndexView(mCurrentMovieIndex + 1, mMoviesListSize);
                mView.updateWidget();
            }
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(List<Movie> movies) {
        mMovies = movies;
        if (mMovies == null) {
            mView.showRefreshButton();
        } else {
            if (mMovies.size() != 0) {
                //Actualizando base de datos
                mMoviesPersistanceUseCase.setMovies(mMovies);

                //Estableciendo elemento actual y tamaño de la base de datos
                mCurrentMovieIndex = 0;
                mMoviesListSize = mMovies.size();
                mMoviesPersistanceUseCase.setCurrentMovieIndex(0);
                mMoviesPersistanceUseCase.setMoviesCount(mMoviesListSize);

                //Configurando la vista
                mView.setupLRButtons();
                Movie movie = null;
                if (mMovies != null && mMovies.size() > 0)
                    movie = mMovies.get(mCurrentMovieIndex);
                mView.hideProgress();
                mView.setupMovieView(movie.getUrl(), movie.getTitle(), movie.getDate());
                mView.setupIndexView(mCurrentMovieIndex + 1, mMoviesListSize);
                mView.updateWidget();
            }
        }
    }
}
