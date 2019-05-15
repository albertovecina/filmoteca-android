package com.vsa.filmoteca.data.repository.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Inject

/**
 * Created by seldon on 27/03/15.
 */
class SharedPreferencesManager @Inject
constructor(context: Context) {

    companion object {
        private const val KEY_NAME = "MySharedPreferences"
        private const val KEY_CURRENT = "Current"
        private const val KEY_SIZE = "Size"
    }

    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)

    var currentMovieIndex: Int
        get() = mSharedPreferences.getInt(KEY_CURRENT, 0)
        set(index) {
            mSharedPreferences
                    .edit()
                    .putInt(KEY_CURRENT, index)
                    .apply()
        }

    var moviesCount: Int
        get() = mSharedPreferences.getInt(KEY_SIZE, 0)
        set(index) {
            mSharedPreferences
                    .edit()
                    .putInt(KEY_SIZE, index)
                    .apply()
        }

}
