package com.vsa.filmoteca.view;

/**
 * Created by seldon on 15/03/15.
 */
public interface EventsWidgetView {
    void initWidget();

    void setupLRButtons();

    void setupMovieView(String url, String title, String date);

    void setupIndexView(int current, int size);

    void updateWidget();

    void showProgress();

    void hideProgress();

    void showRefreshButton();
}
