package com.vsa.filmoteca.internal.di.component

import android.app.Application
import com.vsa.filmoteca.presentation.FilmotecaApplication
import com.vsa.filmoteca.internal.di.scope.PerApplication
import com.vsa.filmoteca.internal.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@PerApplication
@Component(modules = [AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivitiesModule::class,
    ServicesModule::class,
    WidgetsModule::class,
    NetworkingModule::class])
interface ApplicationComponent : AndroidInjector<FilmotecaApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent

    }

}