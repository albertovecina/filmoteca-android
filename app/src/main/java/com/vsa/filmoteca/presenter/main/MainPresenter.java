package com.vsa.filmoteca.presenter.main;

import android.support.v4.widget.SwipeRefreshLayout;

import java.io.Serializable;

/**
 * Created by seldon on 10/03/15.
 */
public interface MainPresenter extends SwipeRefreshLayout.OnRefreshListener {

    void onResume(Serializable movieInfo);

    void onPause();

    void onRefreshMenuButtonClick();

    void onAboutUsButtonClick();

    void onMovieRowClick(int position);

    void loadMovies();

}
