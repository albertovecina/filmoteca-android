package com.vsa.filmoteca.presentation.notifications.di

import com.vsa.filmoteca.presentation.notifications.NotificationPresenter
import com.vsa.filmoteca.presentation.notifications.NotificationPresenterImpl
import com.vsa.filmoteca.view.notifications.NotificationService
import com.vsa.filmoteca.view.notifications.NotificationView
import dagger.Module
import dagger.Provides

/**
 * Created by Alberto Vecina Sánchez on 2019-06-03.
 */
@Module
class NotificationModule {

    @Provides
    fun provideNotificationView(notificationService: NotificationService): NotificationView = notificationService

    @Provides
    fun providesNotificationPresenter(presenter: NotificationPresenterImpl): NotificationPresenter = presenter

}