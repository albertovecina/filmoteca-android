package com.vsa.filmoteca.data.repository.database

import android.provider.BaseColumns

/**
 * Created by seldon on 16/03/15.
 */
class MoviesTable : BaseColumns {

    companion object {
        const val TABLE_NAME = "movies"
        const val COLUMN_TITLE = "title"
        const val COLUMN_SUBTITLE = "subtitle"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE = "date"
        const val COLUMN_URL = "url"
    }

}
