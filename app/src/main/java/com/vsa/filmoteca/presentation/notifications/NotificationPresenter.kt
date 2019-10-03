package com.vsa.filmoteca.presentation.notifications

import com.vsa.filmoteca.presentation.Presenter
import com.vsa.filmoteca.view.notifications.NotificationView

/**
 * Created by Alberto Vecina Sánchez on 2019-06-03.
 */
abstract class NotificationPresenter : Presenter<NotificationView>() {

    abstract fun onNotificationReceived()

    abstract fun onNewToken(token: String)

}