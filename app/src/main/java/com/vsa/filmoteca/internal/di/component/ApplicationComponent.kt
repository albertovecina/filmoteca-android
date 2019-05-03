package com.vsa.filmoteca.internal.di.component

import com.vsa.filmoteca.internal.di.PerApplication
import com.vsa.filmoteca.internal.di.module.ActivityModule
import com.vsa.filmoteca.internal.di.module.ApplicationModule
import com.vsa.filmoteca.internal.di.module.NetworkingModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@PerApplication
@Component(modules = [ApplicationModule::class, NetworkingModule::class])
interface ApplicationComponent {

    fun plusActivityComponent(activityModule: ActivityModule): ActivityComponent

}
