package com.vsa.filmoteca.data.source.repository

interface AppConfigRepository {

    fun initAppConfig()

    fun inAppUpdateEnabled(): Boolean

    fun getMillisUntilReview(): Long

    fun getExecutionsUntilReview(): Long

    var appVisitsCounter: Int

    var fistExecutionTimeMillis: Long

}