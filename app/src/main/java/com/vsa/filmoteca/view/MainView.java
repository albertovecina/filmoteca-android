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

    public void showChangeLog();
    public void showProgressDialog();
    public void hideProgressDialog();
    public void stopRefreshing();
    public void showTitle(int moviesCount);
    public void showWifiRequestDialog(OkCancelDialogListener okCancelDialogListener);
    public void showWifiSettings();
    public void showTimeOutDialog();
    public void showNoEventsDialog();
    public void navigateToDetail(Movie movie);
    public void showAboutUs();
    public void setMovies(List<E> moviesList);
    public boolean isListLoaded();
    public Context getContext();
    public void finish();

}
