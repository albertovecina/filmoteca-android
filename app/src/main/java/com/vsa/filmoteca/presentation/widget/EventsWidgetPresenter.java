package com.vsa.filmoteca.presentation.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.presentation.interactor.MainInteractor;
import com.vsa.filmoteca.presentation.utils.Constants;
import com.vsa.filmoteca.data.repository.database.WidgetDataSource;
import com.vsa.filmoteca.data.repository.sharedpreferences.SharedPreferencesManager;
import com.vsa.filmoteca.view.EventsWidgetView;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;

/**
 * Created by seldon on 27/03/15.
 */
public class EventsWidgetPresenter implements Observer<List<Movie>> {

    private MainInteractor mInteractor;
    private EventsWidgetView mView;

    private int mCurrentMovieIndex = 0;
    private int mMoviesListSize = 0;

    private List<Movie> mMovies;

    private Context mContext;

    @Inject
    public EventsWidgetPresenter(MainInteractor mainInteractor) {
        mInteractor = mainInteractor;
    }

    public void setView(EventsWidgetView view) {
        mView = view;
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mContext = context;
        mView.initWidget(context);
        mView.showProgress();
        mInteractor.moviesList().subscribe(this);
    }

    public void onReceive(Context context, Intent intent) {
        mContext = context;
        mView.initWidget(mContext);
        if (intent.getAction().equals(Constants.ACTION_WIDGET_LEFT) || intent.getAction().equals(Constants.ACTION_WIDGET_RIGHT)) {
            //Obtenemos el indice actual y el tamaño de la base de datos
            mCurrentMovieIndex = SharedPreferencesManager.getCurrentMovieIndex(context);
            mMoviesListSize = SharedPreferencesManager.getMoviesCount(context);
            if (mMoviesListSize != 0) {
                if (intent.getAction().equals(Constants.ACTION_WIDGET_LEFT)) {
                    if (mCurrentMovieIndex > 0) {
                        mCurrentMovieIndex--;
                        //Actualizamos el valor del indice
                        SharedPreferencesManager.setCurrentMovieIndex(context, mCurrentMovieIndex);
                    }
                } else if (intent.getAction().equals(Constants.ACTION_WIDGET_RIGHT)) {
                    if (mCurrentMovieIndex < (mMoviesListSize - 1)) {
                        mCurrentMovieIndex++;
                        //Actualizamos el valor del indice
                        SharedPreferencesManager.setCurrentMovieIndex(context, mCurrentMovieIndex);
                    }
                }
                //Get the movie from database
                WidgetDataSource widgetDataSource = new WidgetDataSource(context);
                widgetDataSource.open();
                Movie movie = widgetDataSource.getMovie(mCurrentMovieIndex);
                widgetDataSource.close();
                //Preparamos la vista
                mView.setupLRButtons(context);
                mView.setupMovieView(context, movie);
                mView.setupIndexView(context, mCurrentMovieIndex + 1, mMoviesListSize);
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
            mView.showRefreshButton(mContext);
        } else {
            if (mMovies.size() != 0) {
                //Actualizando base de datos
                WidgetDataSource widgetDataSource = new WidgetDataSource(mContext);
                widgetDataSource.open();
                widgetDataSource.clearMovies();
                for (int x = 0; x < mMovies.size(); x++)
                    widgetDataSource.insertMovie(x, mMovies.get(x));
                widgetDataSource.close();

                //Estableciendo elemento actual y tamaño de la base de datos
                mCurrentMovieIndex = 0;
                mMoviesListSize = mMovies.size();
                SharedPreferencesManager.setCurrentMovieIndex(mContext, 0);
                SharedPreferencesManager.setMoviesCount(mContext, mMoviesListSize);

                //Configurando la vista
                mView.setupLRButtons(mContext);
                Movie movie = null;
                if (mMovies != null && mMovies.size() > 0)
                    movie = mMovies.get(mCurrentMovieIndex);
                mView.hideProgress();
                mView.setupMovieView(mContext, movie);
                mView.setupIndexView(mContext, mCurrentMovieIndex + 1, mMoviesListSize);
                mView.updateWidget();
            }
        }
    }
}
