package com.vsa.filmoteca.presentation.notifications

import com.vsa.filmoteca.domain.usecase.PushRegistrationTokenUseCase
import com.vsa.filmoteca.view.notifications.NotificationView
import javax.inject.Inject

/**
 * Created by Alberto Vecina Sánchez on 2019-06-03.
 */
class NotificationPresenterImpl @Inject constructor(
        private val view: NotificationView,
        private val pushNotificationTokenUseCase: PushRegistrationTokenUseCase) : NotificationPresenter {

    override fun onNewToken(token: String) {
        pushNotificationTokenUseCase.registerToken(token)
    }

    override fun onNotificationReceived() {
        view.notifyApplication()
        view.updateWidget()
    }

}