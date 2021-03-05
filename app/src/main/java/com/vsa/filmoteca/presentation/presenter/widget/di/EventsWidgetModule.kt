package com.vsa.filmoteca.presentation.presenter.widget.di

import com.vsa.filmoteca.presentation.presenter.widget.EventsWidgetPresenter
import com.vsa.filmoteca.presentation.presenter.widget.EventsWidgetPresenterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
@InstallIn(SingletonComponent::class)
class EventsWidgetModule {

    @Provides
    fun providesEventsWidgetPresenter(eventsWidgetPresenter: EventsWidgetPresenterImpl): EventsWidgetPresenter = eventsWidgetPresenter

}
