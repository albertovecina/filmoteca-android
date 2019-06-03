package com.vsa.filmoteca.internal.di.component

import com.vsa.filmoteca.internal.di.module.ServiceModule
import com.vsa.filmoteca.view.notifications.NotificationService
import dagger.Subcomponent

/**
 * Created by Alberto Vecina Sánchez on 2019-06-03.
 */
@Subcomponent(modules = [ServiceModule::class])
interface ServiceComponent {

    fun inject(notificationService: NotificationService)

}