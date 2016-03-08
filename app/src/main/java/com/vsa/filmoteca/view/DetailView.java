package com.vsa.filmoteca.view;

/**
 * Created by seldon on 13/03/15.
 */
public interface DetailView {

    void hideContent();

    void showContent();

    void showProgressDialog();

    void hideProgressDialog();

    void showMovieTitle(String title);

    void updateWidget();

    void stopRefreshing();

    void showTimeOutDialog();

    void launchBrowser(String url);

    void showShareDialog();

    void showAboutUs();

    void setWebViewContent(String html, String baseUrl);

    void navitgateToComments(String title);

    void onBackPressed();

}
