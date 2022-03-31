package com.vsa.filmoteca.presentation.view.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.vsa.filmoteca.R
import com.vsa.filmoteca.presentation.view.EventsWidgetView
import com.vsa.filmoteca.presentation.view.activity.MoviesListActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EventsWidgetViewImpl @Inject constructor(@ApplicationContext private val context: Context) :
    EventsWidgetView {

    private val views = RemoteViews(context.packageName, R.layout.widget_layout)


    override fun setupLRButtons() {
        views.setOnClickPendingIntent(
            R.id.left, PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, EventsWidgetProvider::class.java).apply {
                    action = EventsWidgetView.ACTION_WIDGET_LEFT
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        views.setOnClickPendingIntent(
            R.id.right, PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, EventsWidgetProvider::class.java).apply {
                    action = EventsWidgetView.ACTION_WIDGET_RIGHT
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun setupMovieView(url: String, title: String, date: String) {
        val intent =
            MoviesListActivity.newIntent(context, Intent.FLAG_ACTIVITY_CLEAR_TOP, url, title, date)
        val actionPendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.widgetInfoLayout, actionPendingIntent)
        views.setTextViewText(R.id.widgetTitleText, title)
        views.setTextViewText(R.id.widgetDateText, date)

    }

    override fun setupIndexView(current: Int, size: Int) {
        views.setTextViewText(R.id.widgetPageText, "$current / $size")
    }

    override fun refreshViews() {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds: IntArray = appWidgetManager.getAppWidgetIds(
            ComponentName(
                context.packageName,
                EventsWidgetProvider::class.java.name
            )
        )
        appWidgetManager.updateAppWidget(appWidgetIds, views)
    }

    override fun showProgress() {
        views.setViewVisibility(R.id.widgetUpdateButton, View.GONE)
        views.setViewVisibility(R.id.widgetInfoLayout, View.GONE)
        views.setViewVisibility(R.id.widgetProgressBar, View.VISIBLE)
    }

    override fun hideProgress() {
        views.setViewVisibility(R.id.widgetProgressBar, View.GONE)
        views.setViewVisibility(R.id.widgetUpdateButton, View.GONE)
        views.setViewVisibility(R.id.widgetInfoLayout, View.VISIBLE)
    }

    override fun showRefreshButton() {
        views.setViewVisibility(R.id.widgetInfoLayout, View.GONE)
        views.setViewVisibility(R.id.widgetProgressBar, View.GONE)
        views.setViewVisibility(R.id.widgetUpdateButton, View.VISIBLE)
        views.setOnClickPendingIntent(
            R.id.widgetUpdateButton,
            PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, EventsWidgetProvider::class.java).apply {
                    action = EventsWidgetView.ACTION_WIDGET_REFRESH
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}