package com.vsa.filmoteca.presentation.presenter.widget.di

import com.vsa.filmoteca.presentation.presenter.widget.EventsWidgetPresenter
import com.vsa.filmoteca.presentation.presenter.widget.EventsWidgetPresenterImpl
import com.vsa.filmoteca.presentation.view.EventsWidgetView
import com.vsa.filmoteca.presentation.view.widget.EventsWidgetViewImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
@InstallIn(SingletonComponent::class)
interface EventsWidgetModule {

    @Binds
    fun bindsEventsWidgetView(view: EventsWidgetViewImpl): EventsWidgetView

    @Binds
    fun bindsEventsWidgetPresenter(eventsWidgetPresenter: EventsWidgetPresenterImpl): EventsWidgetPresenter

}
