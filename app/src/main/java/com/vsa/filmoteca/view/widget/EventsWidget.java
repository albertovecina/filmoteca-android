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
import com.vsa.filmoteca.internal.di.component.ApplicationComponent;
import com.vsa.filmoteca.internal.di.component.DaggerMoviesWidgetComponent;
import com.vsa.filmoteca.internal.di.module.MoviesWidgetModule;
import com.vsa.filmoteca.presentation.utils.Constants;
import com.vsa.filmoteca.presentation.widget.EventsWidgetPresenter;
import com.vsa.filmoteca.view.EventsWidgetView;
import com.vsa.filmoteca.view.activity.MoviesListActivity;

import javax.inject.Inject;

public class EventsWidget extends AppWidgetProvider implements EventsWidgetView {

    private RemoteViews mViews;
    private PendingIntent mPendingIntent;
    private AppWidgetManager mAppWidgetManager;
    private int[] mAppWidgetIds;
    private Context mContext;

    @Inject
    EventsWidgetPresenter mPresenter;

    public EventsWidget() {
        super();
    }


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        initializeContext(context);
        if (mPresenter == null) {
            initializeInjector(context);
            initializePresenter();
        }
        mPresenter.onUpdate();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        initializeContext(context);
        if (mPresenter == null) {
            initializeInjector(context);
            initializePresenter();
        }
        mPresenter.onButtonClick(intent.getAction());
    }

    @Override
    public void initWidget() {
        initializeContext(mContext);
        mViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);
        mAppWidgetManager = AppWidgetManager.getInstance(mContext);
        ComponentName thisAppWidget = new ComponentName(mContext.getPackageName(), EventsWidget.class.getName());
        mAppWidgetIds = mAppWidgetManager.getAppWidgetIds(thisAppWidget);
    }

    @Override
    public void setupLRButtons() {
        //Configurando los botones
        Intent intent = new Intent(mContext, EventsWidget.class);
        intent.setAction(Constants.ACTION_WIDGET_LEFT);
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mViews.setOnClickPendingIntent(R.id.left, mPendingIntent);

        intent = new Intent(mContext, EventsWidget.class);
        intent.setAction(Constants.ACTION_WIDGET_RIGHT);
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mViews.setOnClickPendingIntent(R.id.right, mPendingIntent);
    }

    @Override
    public void setupMovieView(String url, String title, String date) {
        Intent intent = MoviesListActivity.Companion.newIntent(mContext, Intent.FLAG_ACTIVITY_CLEAR_TOP, url, title, date);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mViews.setOnClickPendingIntent(R.id.widgetInfoLayout, actionPendingIntent);
        mViews.setTextViewText(R.id.widgetTitleText, title);
        mViews.setTextViewText(R.id.widgetDateText, date);

    }

    @Override
    public void setupIndexView(int current, int size) {
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
    public void showRefreshButton() {
        mViews.setViewVisibility(R.id.widgetInfoLayout, View.GONE);
        mViews.setViewVisibility(R.id.widgetProgressBar, View.GONE);
        mViews.setViewVisibility(R.id.widgetUpdateButton, View.VISIBLE);
        Intent intent = new Intent(mContext, EventsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = {R.xml.appwidget_info};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        mViews.setOnClickPendingIntent(R.id.widgetUpdateButton, mPendingIntent);
        updateWidget();
    }

    private void initializeContext(Context context) {
        mContext = context;
    }

    private void initializeInjector(Context context) {
        ApplicationComponent applicationComponent = ((FilmotecaApplication) context.getApplicationContext()).getApplicationComponent();
        DaggerMoviesWidgetComponent.builder()
                .applicationComponent(applicationComponent)
                .moviesWidgetModule(new MoviesWidgetModule())
                .build()
                .inject(this);
    }

    private void initializePresenter() {
        mPresenter.setView(this);
    }

}
