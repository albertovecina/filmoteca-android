package com.vsa.filmoteca.presentation.presenter.notifications.di

import android.app.Service
import com.vsa.filmoteca.presentation.presenter.notifications.NotificationPresenter
import com.vsa.filmoteca.presentation.presenter.notifications.NotificationPresenterImpl
import com.vsa.filmoteca.presentation.view.notifications.NotificationView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

/**
 * Created by Alberto Vecina Sánchez on 2019-06-03.
 */
@Module
@InstallIn(ServiceComponent::class)
class NotificationModule {

    @Provides
    fun providesNotificationView(notificationService: Service): NotificationView = notificationService as NotificationView

    @Provides
    fun providesNotificationPresenter(presenter: NotificationPresenterImpl): NotificationPresenter = presenter

}