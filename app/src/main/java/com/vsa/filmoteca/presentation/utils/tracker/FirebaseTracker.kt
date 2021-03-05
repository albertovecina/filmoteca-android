package com.vsa.filmoteca.presentation.utils.tracker

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseTracker @Inject constructor(@ApplicationContext context: Context) : Tracker {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun logAppReviewLaunched() {
        logEvent(Event.ReviewLaunched)
    }

    private fun logEvent(event: Event) {
        firebaseAnalytics.logEvent(event.value, Bundle.EMPTY)
    }

}