package com.vsa.filmoteca.internal.di.module

import android.content.Context
import com.vsa.filmoteca.internal.di.PerWidget
import com.vsa.filmoteca.presentation.widget.EventsWidgetPresenter
import com.vsa.filmoteca.presentation.widget.EventsWidgetPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
class WidgetModule(private val context: Context) {

    @Provides
    fun providesContext(): Context = context

    @PerWidget
    @Provides
    fun providesEventsWidgetPresenter(eventsWidgetPresenter: EventsWidgetPresenterImpl): EventsWidgetPresenter = eventsWidgetPresenter

}
