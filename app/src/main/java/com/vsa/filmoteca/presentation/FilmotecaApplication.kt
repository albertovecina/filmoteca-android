package com.vsa.filmoteca.presentation

import android.app.Application
import com.vsa.filmoteca.domain.usecase.InitAppConfigUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
 * Created by seldon on 31/03/15.
 */
@HiltAndroidApp
class FilmotecaApplication : Application() {

    @Inject
    lateinit var initAppConfigUseCase: InitAppConfigUseCase

    override fun onCreate() {
        super.onCreate()
        initAppConfigUseCase.init()
    }

}
