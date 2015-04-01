package com.vsa.filmoteca.view;

import android.content.Context;

/**
 * Created by seldon on 13/03/15.
 */
public interface DetailView {
    public void showProgressDialog();
    public void hideProgressDialog();
    public void showMovieTitle(String title);
    public void stopRefreshing();
    public void showTimeOutDialog();
    public void showInFilmAffinity();
    public void showInBrowser();
    public void showShareDialog();
    public void showAboutUs();
    public void setWebViewContent(String html);
    public void navitgateToComments(String title);
    public Context getContext();
    public void onBackPressed();
}
