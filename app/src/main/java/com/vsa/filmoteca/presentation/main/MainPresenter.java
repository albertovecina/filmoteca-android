package com.vsa.filmoteca.presentation.main;

import android.support.v4.widget.SwipeRefreshLayout;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.data.domain.dataprovider.MovieDataProvider;
import com.vsa.filmoteca.presentation.interactor.MainInteractor;
import com.vsa.filmoteca.presentation.Presenter;
import com.vsa.filmoteca.view.MainView;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;

/**
 * Created by seldon on 10/03/15.
 */

public class MainPresenter implements SwipeRefreshLayout.OnRefreshListener, OkCancelDialogListener, Presenter<MainView>, Observer<List<Movie>> {

    private List<Movie> mMoviesList;

    private MainInteractor mInteractor;
    private MainView mView;

    @Inject
    public MainPresenter(MainInteractor mainInteractor) {
        mInteractor = mainInteractor;
    }

    public void onCreate(Serializable movieInfo) {
        Movie movie = (Movie) movieInfo;
        loadMovies();
        if (movie != null)
            mView.navigateToDetail(movie.getUrl(), movie.getTitle(), movie.getDate());
    }

    @Override
    public void setView(MainView view) {
        mView = view;
    }

    public void onRefreshMenuButtonClick() {
        loadMovies();
    }

    public void onAboutUsButtonClick() {
        mView.showAboutUs();
    }

    public void onMovieRowClick(int position) {
        Movie movie = mMoviesList.get(position);
        mView.navigateToDetail(movie.getUrl(), movie.getTitle(), movie.getDate());
    }

    public void loadMovies() {
        mView.stopRefreshing();
        mView.showProgressDialog();
        mInteractor.moviesList().subscribe(this);
    }

    public void onAcceptButtonPressed() {
        mView.showWifiSettings();
    }

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
