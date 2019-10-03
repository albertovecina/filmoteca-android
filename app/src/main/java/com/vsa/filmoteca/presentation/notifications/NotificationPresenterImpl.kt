package com.vsa.filmoteca.presentation.notifications

import com.vsa.filmoteca.data.usecase.PushRegistrationTokenUseCase
import javax.inject.Inject

/**
 * Created by Alberto Vecina Sánchez on 2019-06-03.
 */
class NotificationPresenterImpl @Inject constructor(private val pushNotificationTokenUseCase: PushRegistrationTokenUseCase) : NotificationPresenter() {

    override fun onNewToken(token: String) {
        pushNotificationTokenUseCase.registerToken(token)
    }

    override fun onNotificationReceived() {
        view.notifyApplication()
        view.updateWidget()
    }

}