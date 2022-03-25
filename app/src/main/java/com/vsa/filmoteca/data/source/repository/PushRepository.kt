package com.vsa.filmoteca.data.source.repository

import com.vsa.filmoteca.BuildConfig
import com.vsa.filmoteca.data.source.ws.WsInterface
import com.vsa.filmoteca.network.executor.AsyncExecutor
import com.vsa.filmoteca.network.extensions.run
import javax.inject.Inject

/**
 * Created by Alberto Vecina Sánchez on 2019-06-05.
 */
class PushRepository @Inject constructor(
    private val wsInterface: WsInterface,
    private val asyncExecutor: AsyncExecutor
) {

    fun registerPushNotificationToken(token: String) {
        asyncExecutor.execute({
            wsInterface.registerPushNotificationToken(BuildConfig.REGION, token).run()
        })
    }

}