package com.vsa.filmoteca.presentation.presenter.widget.di

import com.vsa.filmoteca.internal.di.scope.PerWidget
import com.vsa.filmoteca.presentation.presenter.widget.EventsWidgetPresenter
import com.vsa.filmoteca.presentation.presenter.widget.EventsWidgetPresenterImpl
import com.vsa.filmoteca.presentation.view.EventsWidgetView
import com.vsa.filmoteca.presentation.view.widget.EventsWidget
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
