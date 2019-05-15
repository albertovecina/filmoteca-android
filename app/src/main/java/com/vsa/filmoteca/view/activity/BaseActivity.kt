package com.vsa.filmoteca.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.vsa.filmoteca.FilmotecaApplication
import com.vsa.filmoteca.internal.di.component.ApplicationComponent

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

abstract class BaseActivity : AppCompatActivity() {

    private val applicationComponent: ApplicationComponent
        get() = (application as FilmotecaApplication).applicationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector(applicationComponent)
    }

    protected abstract fun initializeInjector(applicationComponent: ApplicationComponent)

}
