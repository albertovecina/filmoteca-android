package com.vsa.filmoteca.presentation.movieslist;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.data.usecase.ClearCacheUseCase;
import com.vsa.filmoteca.data.usecase.GetMoviesListUseCase;
import com.vsa.filmoteca.presentation.Presenter;
import com.vsa.filmoteca.view.MoviesListView;
import com.vsa.filmoteca.view.adapter.EventDataProvider;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observer;

/**
 * Created by seldon on 10/03/15.
 */

public class MoviesListPresenter implements OkCancelDialogListener, Presenter<MoviesListView>, EventDataProvider, Observer<List<Movie>> {

    private List<Movie> mMoviesList;

    private ClearCacheUseCase mClearCacheUseCase;
    private GetMoviesListUseCase mGetMoviesListUseCase;
    private MoviesListView mView;

    public MoviesListPresenter(ClearCacheUseCase clearCacheUseCase, GetMoviesListUseCase getMoviesListUseCase) {
        mClearCacheUseCase = clearCacheUseCase;
        mGetMoviesListUseCase = getMoviesListUseCase;
    }

    public void onCreate(String url, String title, String date) {
        onCreate();
        if (url != null)
            mView.navigateToDetail(url, title, date);
    }

    public void onCreate() {
        mClearCacheUseCase.clearExpiredCacheFiles();
        loadMovies();
    }

    @Override
    public void setView(MoviesListView view) {
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
        mGetMoviesListUseCase.moviesList().subscribe(this);
    }

    public void onAcceptButtonPressed() {
        mView.showWifiSettings();
    }

    public void onCancelButtonPressed() {
        mView.finish();
    }

    public void onRefresh() {
        loadMovies();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        mView.showTimeOutDialog();
        mView.hideProgressDialog();
    }

    @Override
    public void onNext(List<Movie> movies) {
        mMoviesList = movies;
        mView.showTitle(mMoviesList.size());
        if (mMoviesList.size() < 1)
            mView.showNoEventsDialog();
        else
            mView.setMovies(this);
        mView.showChangeLog();
        mView.hideProgressDialog();
    }

    @NotNull
    @Override
    public String getTitle(int index) {
        return mMoviesList.get(index).getTitle();
    }

    @NotNull
    @Override
    public String getDate(int index) {
        return mMoviesList.get(index).getDate();
    }

    @Override
    public int getSize() {
        return mMoviesList.size();
    }
}
