package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.internal.di.scope.PerWidget
import com.vsa.filmoteca.presentation.presenter.widget.di.EventsWidgetModule
import com.vsa.filmoteca.presentation.view.widget.EventsWidget
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
abstract class WidgetsModule {

    @ContributesAndroidInjector(modules = [EventsWidgetModule::class])
    @PerWidget
    abstract fun provideEventsWidget(): EventsWidget

}
