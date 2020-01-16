package com.vsa.filmoteca

import com.vsa.filmoteca.internal.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


/**
 * Created by seldon on 31/03/15.
 */
class FilmotecaApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.builder()
                    .application(this)
                    .build()


}
