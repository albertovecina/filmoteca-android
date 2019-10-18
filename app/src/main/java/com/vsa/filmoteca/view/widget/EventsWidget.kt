package com.vsa.filmoteca.view.widget


import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.vsa.filmoteca.FilmotecaApplication
import com.vsa.filmoteca.R
import com.vsa.filmoteca.internal.di.module.WidgetModule
import com.vsa.filmoteca.presentation.widget.EventsWidgetPresenter
import com.vsa.filmoteca.view.EventsWidgetView
import com.vsa.filmoteca.view.activity.MoviesListActivity
import javax.inject.Inject

class EventsWidget : AppWidgetProvider(), EventsWidgetView {

    companion object {
        const val ACTION_WIDGET_LEFT = "ActionReceiverLeft"
        const val ACTION_WIDGET_RIGHT = "ActionReceiverRight"
    }

    private var views: RemoteViews? = null
    private var appWidgetManager: AppWidgetManager? = null
    private var appWidgetIds: IntArray? = null
    private lateinit var context: Context

    @Inject
    lateinit var presenter: EventsWidgetPresenter

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        initializeContext(context)
        initializeInjector(context)
        initializePresenter()
        presenter.onUpdate()
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        initializeContext(context)
        initializeInjector(context)
        initializePresenter()
        if (intent.action == ACTION_WIDGET_LEFT)
            presenter.onButtonLeftClick()
        else if (intent.action == ACTION_WIDGET_RIGHT)
            presenter.onButtonRightClick()
    }

    override fun initWidget() {
        initializeContext(context)
        views = RemoteViews(context.packageName, R.layout.widget_layout)
        appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidget = ComponentName(context.packageName, EventsWidget::class.java.name)
        appWidgetIds = appWidgetManager!!.getAppWidgetIds(thisAppWidget)
    }

    override fun setupLRButtons() {
        views!!.setOnClickPendingIntent(R.id.left,
                PendingIntent.getBroadcast(context, 0, Intent(context, EventsWidget::class.java).apply {
                    action = ACTION_WIDGET_LEFT
                }, PendingIntent.FLAG_UPDATE_CURRENT))
        views!!.setOnClickPendingIntent(R.id.right,
                PendingIntent.getBroadcast(context, 0, Intent(context, EventsWidget::class.java).apply {
                    action = ACTION_WIDGET_RIGHT
                }, PendingIntent.FLAG_UPDATE_CURRENT))
    }

    override fun setupMovieView(url: String, title: String, date: String) {
        val intent = MoviesListActivity.newIntent(context, Intent.FLAG_ACTIVITY_CLEAR_TOP, url, title, date)
        val actionPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        views!!.setOnClickPendingIntent(R.id.widgetInfoLayout, actionPendingIntent)
        views!!.setTextViewText(R.id.widgetTitleText, title)
        views!!.setTextViewText(R.id.widgetDateText, date)

    }

    override fun setupIndexView(current: Int, size: Int) {
        views!!.setTextViewText(R.id.widgetPageText, "$current / $size")
    }

    override fun refreshViews() {
        appWidgetManager!!.updateAppWidget(appWidgetIds, views)
    }

    override fun showProgress() {
        views!!.setViewVisibility(R.id.widgetUpdateButton, View.GONE)
        views!!.setViewVisibility(R.id.widgetInfoLayout, View.GONE)
        views!!.setViewVisibility(R.id.widgetProgressBar, View.VISIBLE)
        refreshViews()
    }

    override fun hideProgress() {
        views!!.setViewVisibility(R.id.widgetProgressBar, View.GONE)
        views!!.setViewVisibility(R.id.widgetUpdateButton, View.GONE)
        views!!.setViewVisibility(R.id.widgetInfoLayout, View.VISIBLE)
        refreshViews()
    }

    override fun showRefreshButton() {
        views!!.setViewVisibility(R.id.widgetInfoLayout, View.GONE)
        views!!.setViewVisibility(R.id.widgetProgressBar, View.GONE)
        views!!.setViewVisibility(R.id.widgetUpdateButton, View.VISIBLE)
        views!!.setOnClickPendingIntent(R.id.widgetUpdateButton,
                PendingIntent.getBroadcast(context, 0,
                        Intent(context, EventsWidget::class.java).apply {
                            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(R.xml.appwidget_info))
                        }, 0))
        refreshViews()
    }

    private fun initializeContext(context: Context) {
        this.context = context
    }

    private fun initializeInjector(context: Context) {
        (context.applicationContext as FilmotecaApplication)
                .applicationComponent
                .plusWidgetComponent(WidgetModule(context))
                .inject(this)
    }

    private fun initializePresenter() {
        presenter.view = this
    }

}
