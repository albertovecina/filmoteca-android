package com.vsa.filmoteca.view.widget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.vsa.filmoteca.FilmotecaApplication;
import com.vsa.filmoteca.R;
import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.presentation.utils.Constants;
import com.vsa.filmoteca.presentation.widget.EventsWidgetPresenter;
import com.vsa.filmoteca.view.EventsWidgetView;
import com.vsa.filmoteca.view.activity.MainActivity;

import javax.inject.Inject;

public class EventsWidget extends AppWidgetProvider implements EventsWidgetView {

    private RemoteViews mViews;
    private PendingIntent mPendingIntent;
    private AppWidgetManager mAppWidgetManager;
    private int[] mAppWidgetIds;

    @Inject
    EventsWidgetPresenter mPresenter;

    public EventsWidget() {
        super();
        initialiceInjector();
        initializePresenter();
    }


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mPresenter.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mPresenter.onReceive(context, intent);
    }

    @Override
    public void initWidget(Context context) {
        mViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        mAppWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), EventsWidget.class.getName());
        mAppWidgetIds = mAppWidgetManager.getAppWidgetIds(thisAppWidget);
    }

    @Override
    public void setupLRButtons(Context context) {
        //Configurando los botones
        Intent intent = new Intent(context, EventsWidget.class);
        intent.setAction(Constants.ACTION_WIDGET_LEFT);
        mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mViews.setOnClickPendingIntent(R.id.left, mPendingIntent);

        intent = new Intent(context, EventsWidget.class);
        intent.setAction(Constants.ACTION_WIDGET_RIGHT);
        mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mViews.setOnClickPendingIntent(R.id.right, mPendingIntent);
    }

    @Override
    public void setupMovieView(Context context, Movie movie) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (movie != null)
            intent.putExtra(MainActivity.EXTRA_MOVIE, movie);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mViews.setOnClickPendingIntent(R.id.widgetInfoLayout, actionPendingIntent);
        mViews.setTextViewText(R.id.widgetTitleText, movie.getTitle());
        mViews.setTextViewText(R.id.widgetDateText, movie.getDate());

    }

    @Override
    public void setupIndexView(Context context, int current, int size) {
        mViews.setTextViewText(R.id.widgetPageText, Integer.toString(current) + " / " + size);
    }

    public void updateWidget() {
        //Actualizando widget
        mAppWidgetManager.updateAppWidget(mAppWidgetIds, mViews);
    }

    @Override
    public void showProgress() {
        mViews.setViewVisibility(R.id.widgetUpdateButton, View.GONE);
        mViews.setViewVisibility(R.id.widgetInfoLayout, View.GONE);
        mViews.setViewVisibility(R.id.widgetProgressBar, View.VISIBLE);
        updateWidget();
    }

    @Override
    public void hideProgress() {
        mViews.setViewVisibility(R.id.widgetProgressBar, View.GONE);
        mViews.setViewVisibility(R.id.widgetUpdateButton, View.GONE);
        mViews.setViewVisibility(R.id.widgetInfoLayout, View.VISIBLE);
        updateWidget();
    }

    @Override
    public void showRefreshButton(Context context) {
        mViews.setViewVisibility(R.id.widgetInfoLayout, View.GONE);
        mViews.setViewVisibility(R.id.widgetProgressBar, View.GONE);
        mViews.setViewVisibility(R.id.widgetUpdateButton, View.VISIBLE);
        Intent intent = new Intent(context, EventsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = {R.xml.appwidget_info};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        mViews.setOnClickPendingIntent(R.id.widgetUpdateButton, mPendingIntent);
        updateWidget();
    }

    private void initialiceInjector() {
        FilmotecaApplication.getInstance().getMainComponent().inject(this);
    }

    private void initializePresenter() {
        mPresenter.setView(this);
    }

}
