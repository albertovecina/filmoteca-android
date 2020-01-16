package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.presentation.notifications.di.NotificationModule
import com.vsa.filmoteca.view.notifications.NotificationService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServicesModule {

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    abstract fun provideNotificationService(): NotificationService

}