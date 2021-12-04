package com.vsa.filmoteca.about.internal.di

import android.app.Activity
import com.vsa.filmoteca.about.presentation.presenter.AboutPresenter
import com.vsa.filmoteca.about.presentation.presenter.AboutPresenterImpl
import com.vsa.filmoteca.about.presentation.view.AboutView
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class AboutModule {

    @Provides
    fun providesView(activity: Activity): AboutView = activity as AboutView

    @Module
    @InstallIn(ActivityComponent::class)
    interface Bindings {

        @Binds
        fun bindsPresenter(presenter: AboutPresenterImpl): AboutPresenter
        
    }

}