package com.vsa.filmoteca.view.notifications

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.vsa.filmoteca.FilmotecaApplication
import com.vsa.filmoteca.internal.di.module.ServiceModule
import com.vsa.filmoteca.presentation.notifications.NotificationPresenter
import com.vsa.filmoteca.view.widget.EventsWidget
import javax.inject.Inject

class NotificationService : FirebaseMessagingService(), NotificationView {

    companion object {
        const val ACTION_NEW_MOVIES = "action_new_movies"
    }

    @Inject
    lateinit var presenter: NotificationPresenter

    override fun onCreate() {
        super.onCreate()
        (application as FilmotecaApplication).applicationComponent
                .plusServiceComponent(ServiceModule())
                .inject(this)
        presenter.view = this
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        presenter.onNotificationReceived()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        presenter.onNewToken(token)
    }

    override fun notifyApplication() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(ACTION_NEW_MOVIES))
    }

    override fun updateWidget() {
        val ids = AppWidgetManager.getInstance(application)
                .getAppWidgetIds(ComponentName(this, EventsWidget::class.java))
        sendBroadcast(Intent(this, EventsWidget::class.java)
                .apply {
                    action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
                })
    }

}
