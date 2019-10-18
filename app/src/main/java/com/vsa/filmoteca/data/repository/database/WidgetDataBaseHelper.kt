package com.vsa.filmoteca.data.repository.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class WidgetDataBaseHelper @JvmOverloads constructor(context: Context, name: String = DATABASE_NAME, factory: CursorFactory? = null,
                                                     version: Int = DATABASE_VERSION) : SQLiteOpenHelper(context, name, factory, version) {

    private var sqlQueryCreateUsersTable = "CREATE TABLE " + MoviesTable.TABLE_NAME + " (cod INTEGER, " +
            MoviesTable.COLUMN_TITLE + " TEXT, " +
            MoviesTable.COLUMN_SUBTITLE + " TEXT, " +
            MoviesTable.COLUMN_DESCRIPTION + " TEXT, " +
            MoviesTable.COLUMN_DATE + " TEXT, " +
            MoviesTable.COLUMN_URL + " TEXT)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sqlQueryCreateUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, arg1: Int, arg2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIES")
        db.execSQL(sqlQueryCreateUsersTable)
    }

    companion object {

        const val DATABASE_NAME = "WidgetDatabase.db"
        const val DATABASE_VERSION = 2

        const val TABLE_MOVIES = "movies"

    }

}
