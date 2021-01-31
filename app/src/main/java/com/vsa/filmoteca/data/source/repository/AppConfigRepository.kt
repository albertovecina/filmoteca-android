package com.vsa.filmoteca.data.source.repository

interface AppConfigRepository {

    fun initAppConfig()

    fun inAppReviewsEnabled(): Boolean

    fun getMillisUntilReview(): Long

    fun getExecutionsUntilReview(): Long

    var isReviewAlreadySuggested: Boolean

    var appVisitsCounter: Int

    var fistExecutionTimeMillis: Long

}