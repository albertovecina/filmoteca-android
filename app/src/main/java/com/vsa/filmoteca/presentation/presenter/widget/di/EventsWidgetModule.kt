package com.vsa.filmoteca.presentation.widget.di

import com.vsa.filmoteca.internal.di.scope.PerWidget
import com.vsa.filmoteca.presentation.widget.EventsWidgetPresenter
import com.vsa.filmoteca.presentation.widget.EventsWidgetPresenterImpl
import com.vsa.filmoteca.view.EventsWidgetView
import com.vsa.filmoteca.view.widget.EventsWidget
import dagger.Module
import dagger.Provides

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
class EventsWidgetModule {

    @PerWidget
    @Provides
    fun providesEventsWidgetView(eventsWidget: EventsWidget): EventsWidgetView = eventsWidget

    @PerWidget
    @Provides
    fun providesEventsWidgetPresenter(eventsWidgetPresenter: EventsWidgetPresenterImpl): EventsWidgetPresenter = eventsWidgetPresenter

}
