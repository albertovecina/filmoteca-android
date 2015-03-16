package com.vsa.filmoteca.widget;

import android.content.Context;

/**
 * Created by seldon on 15/03/15.
 */
public interface EventsWidgetView {
    public void initWidget(Context context);
    public void showProgress();
    public void hideProgress();
    public void showRefreshButton();
}
