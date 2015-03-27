package com.vsa.filmoteca.view;

import android.content.Context;

import com.vsa.filmoteca.model.Movie;

/**
 * Created by seldon on 15/03/15.
 */
public interface EventsWidgetView {
    public void initWidget(Context context);
    public void setupLRButtons(Context context);
    public void setupMovieView(Context context, Movie movie);
    public void setupIndexView(Context context, int current, int size);
    public void updateWidget();
    public void showProgress();
    public void hideProgress();
    public void showRefreshButton(Context context);
}
