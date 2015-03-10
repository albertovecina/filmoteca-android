package com.vsa.filmoteca;

import android.content.Context;

import com.vsa.filmoteca.dialog.interfaces.OkCancelDialogListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by seldon on 10/03/15.
 */
public interface MainView {

    public void initViews();
    public void showChangeLog();
    public void showProgressDialog();
    public void hideProgressDialog();
    public void showWifiRequestDialog(OkCancelDialogListener okCancelDialogListener);
    public void showWifiSettings();
    public void showTimeOutDialog();
    public void showDetail(HashMap<String, String> movie);
    public void showAboutUs();
    public void setMovies(List<HashMap<String, String>> moviesList);
    public boolean isListLoaded();
    public Context getContext();
    public void finish();

}
