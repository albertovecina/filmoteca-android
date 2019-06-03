package com.vsa.filmoteca.data.usecase

import com.vsa.filmoteca.data.repository.PushRepository
import javax.inject.Inject

/**
 * Created by Alberto Vecina Sánchez on 2019-06-05.
 */
class PushRegistrationTokenUseCase @Inject constructor(private val pushRepository: PushRepository) {

    fun registerToken(token: String) = pushRepository.registerPushNotificationToken(token)

}