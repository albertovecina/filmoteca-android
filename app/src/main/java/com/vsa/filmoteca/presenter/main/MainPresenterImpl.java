package com.vsa.filmoteca.presenter.main;

import com.vsa.filmoteca.interactor.FilmotecaInteractor;
import com.vsa.filmoteca.interactor.FilmotecaInteractorImpl;
import com.vsa.filmoteca.model.domain.Movie;
import com.vsa.filmoteca.model.domain.dataprovider.MovieDataProvider;
import com.vsa.filmoteca.model.event.BUS;
import com.vsa.filmoteca.view.MainView;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;

import java.io.Serializable;
import java.util.List;

import rx.Observer;

/**
 * Created by seldon on 10/03/15.
 */

public class MainPresenterImpl implements MainPresenter, OkCancelDialogListener, Observer<List<Movie>> {

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
        mInteractor.moviesList().subscribe(this);
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

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        mView.showTimeOutDialog();
    }

    @Override
    public void onNext(List<Movie> movies) {
        mMoviesList = movies;
        mView.showTitle(mMoviesList.size());
        if (mMoviesList.size() < 1)
            mView.showNoEventsDialog();
        else
            mView.setMovies(mMoviesList, new MovieDataProvider());
        mView.showChangeLog();
        mView.hideProgressDialog();
    }

}
