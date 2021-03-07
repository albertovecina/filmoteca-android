package com.vsa.filmoteca.presentation.view.notifications

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.vsa.filmoteca.presentation.presenter.notifications.NotificationPresenter
import com.vsa.filmoteca.presentation.view.EventsWidgetView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : FirebaseMessagingService(), NotificationView {

    companion object {
        const val ACTION_NEW_MOVIES = "action_new_movies"
    }

    @Inject
    lateinit var presenter: NotificationPresenter

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
        sendBroadcast(Intent(EventsWidgetView.ACTION_WIDGET_REFRESH))
    }

}
