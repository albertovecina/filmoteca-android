package com.vsa.filmoteca.data.source.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.vsa.filmoteca.R
import com.vsa.filmoteca.data.source.sharedpreferences.SharedPreferencesManager
import javax.inject.Inject

class AppConfigRepositoryImpl @Inject constructor(private val preferencesManager: SharedPreferencesManager) : AppConfigRepository {

    companion object {
        const val KEY_IN_APP_REVIEWS_ENABLED = "in_app_reviews_enabled"
        const val KEY_MILLIS_UNTIL_REVIEW = "millis_until_review"
        const val KEY_EXECUTIONS_UNTIL_REVIEW = "executions_until_review"
    }

    override fun initAppConfig() {
        with(FirebaseRemoteConfig.getInstance()) {
            setDefaultsAsync(R.xml.remote_config_defaults)
                    .addOnCompleteListener {
                        fetchAndActivate()
                    }
        }
    }

    override fun inAppReviewsEnabled(): Boolean =
            FirebaseRemoteConfig.getInstance().getBoolean(KEY_IN_APP_REVIEWS_ENABLED)

    override fun getMillisUntilReview(): Long =
            FirebaseRemoteConfig.getInstance().getLong(KEY_MILLIS_UNTIL_REVIEW)

    override fun getExecutionsUntilReview(): Long =
            FirebaseRemoteConfig.getInstance().getLong(KEY_EXECUTIONS_UNTIL_REVIEW)

    override var isReviewAlreadySuggested: Boolean
        get() = preferencesManager.isReviewAlreadySuggested
        set(value) {
            preferencesManager.isReviewAlreadySuggested = value
        }

    override var appVisitsCounter: Int
        get() = preferencesManager.appVisitsCounter
        set(value) {
            preferencesManager.appVisitsCounter = value
        }

    override var fistExecutionTimeMillis: Long
        get() = preferencesManager.fistExecutionTimeMillis
        set(value) {
            preferencesManager.fistExecutionTimeMillis = value
        }

}