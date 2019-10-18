package com.vsa.filmoteca.data.repository

import com.vsa.filmoteca.data.repository.ws.WsInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Alberto Vecina Sánchez on 2019-06-05.
 */
class PushRepository @Inject constructor(private val wsInterface: WsInterface) {

    fun registerPushNotificationToken(token: String) {
        wsInterface.registerPusHNotificationToken(token).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

            }
        })
    }

}