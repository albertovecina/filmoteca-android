package com.vsa.filmoteca.internal.di.component

import com.vsa.filmoteca.internal.di.PerApplication
import com.vsa.filmoteca.internal.di.module.*
import dagger.Component


/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@PerApplication
@Component(modules = [ApplicationModule::class, NetworkingModule::class])
interface ApplicationComponent {

    fun plusActivityComponent(activityModule: ActivityModule): ActivityComponent

    fun plusWidgetComponent(widgetModule: WidgetModule): WidgetComponent

    fun plusServiceComponent(serviceModule: ServiceModule): ServiceComponent

}
