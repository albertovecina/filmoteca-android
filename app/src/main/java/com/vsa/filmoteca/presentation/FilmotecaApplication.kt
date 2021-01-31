package com.vsa.filmoteca.presentation

import com.vsa.filmoteca.domain.usecase.InitAppConfigUseCase
import com.vsa.filmoteca.internal.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject


/**
 * Created by seldon on 31/03/15.
 */
class FilmotecaApplication : DaggerApplication() {

    @Inject
    lateinit var initAppConfigUseCase: InitAppConfigUseCase

    override fun onCreate() {
        super.onCreate()
        initAppConfigUseCase.init()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.builder()
                    .application(this)
                    .build()


}
