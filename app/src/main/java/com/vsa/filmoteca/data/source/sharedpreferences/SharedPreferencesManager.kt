package com.vsa.filmoteca.data.source.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by seldon on 27/03/15.
 */
class SharedPreferencesManager @Inject constructor(context: Context) {

    companion object {
        private const val KEY_NAME = "MySharedPreferences"
        private const val KEY_CURRENT = "Current"
        private const val KEY_SIZE = "Size"
        private const val KEY_FIRST_EXECUTION_TIME_MILLIS = "key_first_execution_time_millis"
        private const val KEY_APP_VISITS = "key_app_visits"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)

    var currentMovieIndex: Int
        get() = sharedPreferences.getInt(KEY_CURRENT, 0)
        set(index) {
            sharedPreferences
                    .edit()
                    .putInt(KEY_CURRENT, index)
                    .apply()
        }

    var moviesCount: Int
        get() = sharedPreferences.getInt(KEY_SIZE, 0)
        set(index) {
            sharedPreferences
                    .edit()
                    .putInt(KEY_SIZE, index)
                    .apply()
        }

    var fistExecutionTimeMillis: Long
        get() = sharedPreferences.getLong(KEY_FIRST_EXECUTION_TIME_MILLIS, -1)
        set(value) {
            sharedPreferences.edit()
                    .putLong(KEY_FIRST_EXECUTION_TIME_MILLIS, value)
                    .apply()
        }

    var appVisitsCounter: Int
        get() = sharedPreferences.getInt(KEY_APP_VISITS, 0)
        set(value) {
            sharedPreferences.edit()
                    .putInt(KEY_APP_VISITS, value)
                    .apply()
        }

}
