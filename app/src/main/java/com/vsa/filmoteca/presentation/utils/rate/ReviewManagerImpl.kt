package com.vsa.filmoteca.presentation.utils.rate

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory
import com.vsa.filmoteca.data.source.repository.AppConfigRepository
import com.vsa.filmoteca.presentation.utils.tracker.Tracker
import javax.inject.Inject

class ReviewManagerImpl @Inject constructor(
        private val activity: Activity,
        private val appConfigRepository: AppConfigRepository,
        private val tracker: Tracker
) : ReviewManager {

    override fun showRateIfNecessary() {
        if (appConfigRepository.inAppUpdateEnabled())
            if (hasBeenInstalledForAWhile() && hasBeenExecutedEnough())
                showRateView()
    }

    override fun increaseAppVisits() {
        val appVisitsCounter = appConfigRepository.appVisitsCounter
        if (appVisitsCounter == 0)
            appConfigRepository.fistExecutionTimeMillis = System.currentTimeMillis()
        appConfigRepository.appVisitsCounter = appVisitsCounter + 1
    }

    private fun showRateView() {
        val manager = ReviewManagerFactory.create(activity)
        manager.requestReviewFlow().addOnCompleteListener { response ->
            if (response.isSuccessful) {
                manager.launchReviewFlow(activity, response.result)
                        .addOnCompleteListener {
                            tracker.logAppReviewLaunched()
                        }
            }
        }
    }


    private fun hasBeenInstalledForAWhile(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        val firstExecutionTimeMillis = appConfigRepository.fistExecutionTimeMillis
        val millisUntilRate = appConfigRepository.getMillisUntilRate()
        return (firstExecutionTimeMillis - currentTimeMillis) > millisUntilRate
    }

    private fun hasBeenExecutedEnough(): Boolean {
        return appConfigRepository.appVisitsCounter > 3
    }


}