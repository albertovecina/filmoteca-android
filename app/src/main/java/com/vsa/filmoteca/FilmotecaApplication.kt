package com.vsa.filmoteca

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log

import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.vsa.filmoteca.internal.di.component.ApplicationComponent
import com.vsa.filmoteca.internal.di.component.DaggerApplicationComponent
import com.vsa.filmoteca.internal.di.module.ApplicationModule
import com.vsa.filmoteca.internal.di.module.NetworkingModule
import com.vsa.filmoteca.presentation.utils.ConnectivityUtils


/**
 * Created by seldon on 31/03/15.
 */
class FilmotecaApplication : Application() {

    companion object {

        private val TAG = FilmotecaApplication::class.java.simpleName

        lateinit var instance: FilmotecaApplication

    }

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeApplicationComponent()
        if (ConnectivityUtils.isInternetAvailable(this)) {
            initTwitter()
        }
    }

    private fun initTwitter() {
        try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            val bundle = applicationInfo.metaData
            val apiKey = bundle.getString("io.twitter.ApiKey")
            val apiSecret = bundle.getString("io.twitter.ApiSecret")
            val authConfig = TwitterAuthConfig(apiKey!!,
                    apiSecret!!)
            val twitterConfig = TwitterConfig.Builder(this)
                    .twitterAuthConfig(authConfig)
                    .debug(true)
                    .build()
            Twitter.initialize(twitterConfig)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.message)
        } catch (e: NullPointerException) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.message)
        }

    }

    private fun initializeApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkingModule(NetworkingModule(this))
                .build()
    }

}
