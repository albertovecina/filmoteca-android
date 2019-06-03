package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.presentation.notifications.NotificationPresenter
import com.vsa.filmoteca.presentation.notifications.NotificationPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by Alberto Vecina Sánchez on 2019-06-03.
 */
@Module
class ServiceModule {

    @Provides
    fun providesNotificationPresenter(presenter: NotificationPresenterImpl): NotificationPresenter = presenter

}