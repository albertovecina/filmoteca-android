package com.vsa.filmoteca.view;

import android.content.Context;

/**
 * Created by seldon on 13/03/15.
 */
public interface DetailView {
    void showProgressDialog();

    void hideProgressDialog();

    void showMovieTitle(String title);

    void stopRefreshing();

    void showTimeOutDialog();

    void launchBrouser(String url);

    void showShareDialog();

    void showAboutUs();

    void setWebViewContent(String html, String baseUrl);

    void navitgateToComments(String title);

    Context getContext();

    void onBackPressed();
}
