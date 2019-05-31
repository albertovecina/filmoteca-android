package com.vsa.filmoteca

import android.app.Application
import com.vsa.filmoteca.internal.di.component.ApplicationComponent
import com.vsa.filmoteca.internal.di.component.DaggerApplicationComponent
import com.vsa.filmoteca.internal.di.module.ApplicationModule
import com.vsa.filmoteca.internal.di.module.NetworkingModule


/**
 * Created by seldon on 31/03/15.
 */
class FilmotecaApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initializeApplicationComponent()
    }

    private fun initializeApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkingModule(NetworkingModule(this))
                .build()
    }

}
