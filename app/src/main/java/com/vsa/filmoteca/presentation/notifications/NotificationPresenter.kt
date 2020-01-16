package com.vsa.filmoteca.presentation.notifications

/**
 * Created by Alberto Vecina Sánchez on 2019-06-03.
 */
interface NotificationPresenter {

    fun onNotificationReceived()

    fun onNewToken(token: String)

}