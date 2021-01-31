package com.vsa.filmoteca.data.source.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.vsa.filmoteca.R
import javax.inject.Inject

class AppConfigRepositoryImpl @Inject constructor() : AppConfigRepository {

    companion object {
        const val KEY_IN_APP_REVIEWS_ENABLED = "in_app_reviews_enabled"
        const val MILLIS_UNTIL_REVIEW = "millis_until_review"
    }

    override fun initAppConfig() {
        with(FirebaseRemoteConfig.getInstance()) {
            setDefaultsAsync(R.xml.remote_config_defaults)
                    .addOnCompleteListener {
                        fetchAndActivate()
                    }
        }
    }

    override fun inAppUpdateEnabled(): Boolean =
            FirebaseRemoteConfig.getInstance().getBoolean(KEY_IN_APP_REVIEWS_ENABLED)

    override fun getMillisUntilRate(): Long =
            FirebaseRemoteConfig.getInstance().getLong(MILLIS_UNTIL_REVIEW)

}