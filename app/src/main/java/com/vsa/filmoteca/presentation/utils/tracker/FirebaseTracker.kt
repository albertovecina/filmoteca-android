package com.vsa.filmoteca.presentation.utils.tracker

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseTracker @Inject constructor(context: Context) : Tracker {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun logAppReviewLaunched() {
        logEvent(Event.ReviewLaunched)
    }

    private fun logEvent(event: Event) {
        firebaseAnalytics.logEvent(event.value, Bundle.EMPTY)
    }

}