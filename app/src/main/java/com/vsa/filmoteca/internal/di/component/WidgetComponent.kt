package com.vsa.filmoteca.internal.di.component

import com.vsa.filmoteca.internal.di.PerWidget
import com.vsa.filmoteca.internal.di.module.WidgetModule
import com.vsa.filmoteca.view.widget.EventsWidget
import dagger.Subcomponent

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@PerWidget
@Subcomponent(modules = [WidgetModule::class])
interface WidgetComponent {

    fun inject(eventsWidget: EventsWidget)

}
