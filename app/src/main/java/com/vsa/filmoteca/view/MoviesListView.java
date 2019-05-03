package com.vsa.filmoteca.view;

import com.vsa.filmoteca.view.adapter.EventDataProvider;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;

/**
 * Created by seldon on 10/03/15.
 */
public interface MoviesListView {

    void showChangeLog();

    void showProgressDialog();

    void hideProgressDialog();

    void stopRefreshing();

    void showTitle(int moviesCount);

    void showWifiRequestDialog(OkCancelDialogListener okCancelDialogListener);

    void showWifiSettings();

    void showTimeOutDialog();

    void showNoEventsDialog();

    void navigateToDetail(String url, String title, String date);

    void showAboutUs();

    void setMovies(EventDataProvider dataProvider);

    boolean isListLoaded();

    void finish();

}
