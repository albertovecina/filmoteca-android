package com.vsa.filmoteca.data.source.repository

interface AppConfigRepository {

    fun initAppConfig()

    fun inAppUpdateEnabled(): Boolean

    fun getMillisUntilRate(): Long

}