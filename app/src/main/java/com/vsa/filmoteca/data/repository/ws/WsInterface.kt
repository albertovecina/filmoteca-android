package com.vsa.filmoteca.data.repository.ws

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Alberto Vecina Sánchez on 2019-06-05.
 */
interface WsInterface {

    @POST("registrationId")
    fun registerPusHNotificationToken(@Query("token") token: String): Call<Void>

}