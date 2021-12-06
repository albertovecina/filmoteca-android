package com.vsa.filmoteca.about.internal.di

import androidx.fragment.app.Fragment
import com.vsa.filmoteca.about.presentation.presenter.AboutPresenter
import com.vsa.filmoteca.about.presentation.presenter.AboutPresenterImpl
import com.vsa.filmoteca.about.presentation.view.AboutView
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class AboutModule {

    @Provides
    fun providesView(fragment: Fragment): AboutView = fragment as AboutView

    @Module
    @InstallIn(FragmentComponent::class)
    interface Bindings {

        @Binds
        fun bindsPresenter(presenter: AboutPresenterImpl): AboutPresenter

    }

}