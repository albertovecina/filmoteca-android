package com.vsa.filmoteca.view;

import android.content.Context;

import com.vsa.filmoteca.model.Movie;
import com.vsa.filmoteca.view.adapter.EventsAdapter;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by seldon on 10/03/15.
 */
public interface MainView<E extends EventsAdapter.Event> {

    void showChangeLog();

    void showProgressDialog();

    void hideProgressDialog();

    void stopRefreshing();

    void showTitle(int moviesCount);

    void showWifiRequestDialog(OkCancelDialogListener okCancelDialogListener);

    void showWifiSettings();

    void showTimeOutDialog();

    void showNoEventsDialog();

    void navigateToDetail(Movie movie);

    void showAboutUs();

    void setMovies(List<E> moviesList);

    boolean isListLoaded();

    void finish();

}
