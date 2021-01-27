package com.vsa.filmoteca.presentation.utils.rate

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory
import com.vsa.filmoteca.data.source.sharedpreferences.SharedPreferencesManager
import javax.inject.Inject

class RateManagerImpl @Inject constructor(
        private val activity: Activity,
        private val sharedPreferencesManager: SharedPreferencesManager
) : RateManager {

    override fun showRateIfNecessary() {
        if (hasBeenInstalledForAWhile() && hasBeenExecutedEnough())
            showRateView()
    }

    override fun increaseAppVisits() {
        val appVisitsCounter = sharedPreferencesManager.appVisitsCounter
        if (appVisitsCounter == 0)
            sharedPreferencesManager.fistExecutionTimeMillis = System.currentTimeMillis()
        sharedPreferencesManager.appVisitsCounter = appVisitsCounter + 1
    }

    private fun showRateView() {
        val manager = ReviewManagerFactory.create(activity)
        manager.requestReviewFlow().addOnCompleteListener { response ->
            if (response.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = response.result
                val flow = manager.launchReviewFlow(activity, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                }
            }
        }
    }


    private fun hasBeenInstalledForAWhile(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        val firstExecutionTimeMillis = sharedPreferencesManager.fistExecutionTimeMillis

        return (firstExecutionTimeMillis - currentTimeMillis) > 1000 * 60 * 60 * 24 * 7
    }

    private fun hasBeenExecutedEnough(): Boolean {
        return sharedPreferencesManager.appVisitsCounter > 3
    }


}