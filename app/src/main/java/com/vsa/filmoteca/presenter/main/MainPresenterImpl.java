package com.vsa.filmoteca.presenter.main;

import com.squareup.otto.Subscribe;
import com.vsa.filmoteca.interactor.FilmotecaInteractorImpl;
import com.vsa.filmoteca.interactor.FilmotecaInteractor;
import com.vsa.filmoteca.model.Movie;
import com.vsa.filmoteca.model.MoviesFactory;
import com.vsa.filmoteca.model.event.BUS;
import com.vsa.filmoteca.model.event.main.EventGetHtmlError;
import com.vsa.filmoteca.model.event.main.EventGetHtmlSuccess;
import com.vsa.filmoteca.presenter.utils.Constants;
import com.vsa.filmoteca.view.MainView;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;

import java.io.Serializable;
import java.util.List;

/**
 * Created by seldon on 10/03/15.
 */

public class MainPresenterImpl implements MainPresenter, OkCancelDialogListener {

    private List<Movie> mMoviesList;

    private FilmotecaInteractor mInteractor = new FilmotecaInteractorImpl();
    private MainView mView;

    public MainPresenterImpl(MainView mainView) {
        mView = mainView;
    }

    @Override
    public void onResume(Serializable movieInfo) {
        BUS.getInstance().register(this);
        Movie movie = (Movie) movieInfo;
        if (movie != null) {
            mView.navigateToDetail(movie);
        } else {
            if (!mView.isListLoaded()) {
                if (!mInteractor.isNetworkAvailable()) {
                    mView.showWifiRequestDialog(this);
                } else {
                    loadMovies();
                }
            }
        }
    }

    @Override
    public void onPause() {
        BUS.getInstance().unregister(this);
    }

    @Override
    public void onRefreshMenuButtonClick() {
        loadMovies();
    }

    @Override
    public void onAboutUsButtonClick() {
        mView.showAboutUs();
    }

    @Override
    public void onMovieRowClick(int position) {
        mView.navigateToDetail(mMoviesList.get(position));
    }

    @Override
    public void loadMovies() {
        mView.stopRefreshing();
        mView.showProgressDialog();
        mInteractor.getHtml(Constants.URL_SOURCE);
    }

    @Subscribe
    public void onGetHmltSuccess(EventGetHtmlSuccess event) {
        mMoviesList = MoviesFactory.parseMoviesList(event.getHtml());
        mView.showTitle(mMoviesList.size());
        if (mMoviesList.size() < 1)
            mView.showNoEventsDialog();
        else
            mView.setMovies(mMoviesList);
        mView.showChangeLog();
        mView.hideProgressDialog();
    }

    @Subscribe
    public void onGetHtmlError(EventGetHtmlError event) {
        mView.showTimeOutDialog();
    }

    @Override
    public void onAcceptButtonPressed() {
        mView.showWifiSettings();
    }

    @Override
    public void onCancelButtonPressed() {
        mView.finish();
    }

    @Override
    public void onRefresh() {
        loadMovies();
    }
}
