package com.vsa.filmoteca.presenter;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

/**
 * Created by seldon on 27/03/15.
 */
public interface EventsWidgetPresenter {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds);
    public void onReceive(Context context, Intent intent);
    public void loadMovies();

}
