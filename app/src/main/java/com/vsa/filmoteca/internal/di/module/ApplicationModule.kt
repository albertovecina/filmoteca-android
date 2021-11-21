package com.vsa.filmoteca.internal.di.module

import com.vsa.filmoteca.data.net.AsyncExecutor
import com.vsa.filmoteca.data.net.CoroutineAsyncExecutor
import com.vsa.filmoteca.presentation.tracker.FirebaseTracker
import com.vsa.filmoteca.presentation.tracker.Tracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationModule {

    @Binds
    @Singleton
    fun bindsTracker(tracker: FirebaseTracker): Tracker

    @Binds
    @Singleton
    fun bindsAsyncExecutor(coroutineAsyncExecutor: CoroutineAsyncExecutor): AsyncExecutor

}
