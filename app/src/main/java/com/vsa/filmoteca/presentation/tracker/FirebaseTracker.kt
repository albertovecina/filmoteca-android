package com.vsa.filmoteca.presentation.tracker

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseTracker @Inject constructor(@ApplicationContext context: Context) : Tracker {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun logAppReviewRequest() {
        logEvent(Event.AppReviewRequest)
    }

    override fun logClickMovieItem(title: String) {
        logEvent(Event.ClickMovieItem, Bundle().apply { putString(Event.PARAM_MOVIE_TITLE, title) })
    }

    override fun logClickFilmAffinity(title: String) {
        logEvent(Event.ClickFilmAffinity, Bundle().apply { putString(Event.PARAM_MOVIE_TITLE, title) })
    }

    override fun logClickWebsite(title: String) {
        logEvent(Event.ClickWebSite, Bundle().apply { putString(Event.PARAM_MOVIE_TITLE, title) })
    }

    override fun logClickShareMovie(title: String) {
        logEvent(Event.ClickShareMovie, Bundle().apply { putString(Event.PARAM_MOVIE_TITLE, title) })
    }

    private fun logEvent(event: Event, params: Bundle = Bundle.EMPTY) {
        firebaseAnalytics.logEvent(event.name, params)
    }

}