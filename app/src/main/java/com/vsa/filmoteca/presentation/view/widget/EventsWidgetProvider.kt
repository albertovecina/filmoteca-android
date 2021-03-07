package com.vsa.filmoteca.presentation.view.widget


import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import com.vsa.filmoteca.presentation.presenter.widget.EventsWidgetPresenter
import com.vsa.filmoteca.presentation.view.EventsWidgetView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EventsWidgetProvider : AppWidgetProvider() {

    @Inject
    lateinit var presenter: EventsWidgetPresenter

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        presenter.onRefresh()
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        when (intent.action) {
            EventsWidgetView.ACTION_WIDGET_LEFT -> presenter.onButtonLeftClick()
            EventsWidgetView.ACTION_WIDGET_RIGHT -> presenter.onButtonRightClick()
            EventsWidgetView.ACTION_WIDGET_REFRESH -> presenter.onRefresh()
        }
    }

}
