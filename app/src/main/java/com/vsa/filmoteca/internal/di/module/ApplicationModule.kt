package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.presentation.tracker.FirebaseTracker
import com.vsa.filmoteca.presentation.tracker.Tracker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideTracker(tracker: FirebaseTracker): Tracker = tracker

}
