package com.vsa.filmoteca.view;

import com.vsa.filmoteca.model.domain.Movie;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;
import com.vsa.paperknife.CellDataProvider;
import com.vsa.paperknife.CellElement;

import java.util.List;

/**
 * Created by seldon on 10/03/15.
 */
public interface MainView {

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

    void setMovies(List<? extends CellElement> events, CellDataProvider cellDataProvider);

    boolean isListLoaded();

    void finish();

}
