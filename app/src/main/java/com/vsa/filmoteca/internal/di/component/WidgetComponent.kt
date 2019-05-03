package com.vsa.filmoteca.internal.di.component

import com.vsa.filmoteca.internal.di.PerApplication
import com.vsa.filmoteca.internal.di.module.NetworkingModule
import com.vsa.filmoteca.internal.di.module.WidgetModule
import com.vsa.filmoteca.view.widget.EventsWidget
import dagger.Component

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@PerApplication
@Component(modules = [WidgetModule::class, NetworkingModule::class])
interface WidgetComponent {

    fun inject(eventsWidget: EventsWidget)

}
