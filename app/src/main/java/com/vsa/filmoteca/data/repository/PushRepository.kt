package com.vsa.filmoteca.data.repository

import com.vsa.filmoteca.data.repository.ws.WsInterface
import javax.inject.Inject

/**
 * Created by Alberto Vecina Sánchez on 2019-06-05.
 */
class PushRepository @Inject constructor(private val wsInterface: WsInterface) {

    fun registerPushNotificationToken(token: String) {
        wsInterface.registerPusHNotificationToken(token).execute()
    }

}