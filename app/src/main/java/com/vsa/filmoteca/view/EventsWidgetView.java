package com.vsa.filmoteca.view;

import android.content.Context;

import com.vsa.filmoteca.model.Movie;

/**
 * Created by seldon on 15/03/15.
 */
public interface EventsWidgetView {
    void initWidget(Context context);

    void setupLRButtons(Context context);

    void setupMovieView(Context context, Movie movie);

    void setupIndexView(Context context, int current, int size);

    void updateWidget();

    void showProgress();

    void hideProgress();

    void showRefreshButton(Context context);
}
