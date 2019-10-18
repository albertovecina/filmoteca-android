package com.vsa.filmoteca.data.repository.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import com.vsa.filmoteca.data.domain.Movie
import javax.inject.Inject

/**
 * Created by seldon on 16/03/15.
 */
class MoviesDataBaseSource @Inject constructor(context: Context) {

    private var database: SQLiteDatabase? = null
    private val databaseHelper: WidgetDataBaseHelper = WidgetDataBaseHelper(context)


    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
    }

    fun insertMovie(index: Int, movie: Movie) {
        database!!.execSQL("INSERT INTO " + WidgetDataBaseHelper.TABLE_MOVIES +
                " VALUES (" +
                Integer.toString(index) + ",'" +
                movie.title.replace("'", "") + "','" +
                movie.subtitle.replace("'", "") + "','" +
                "description','" +
                movie.date + "','" +
                movie.url + "')")
    }

    fun getMovie(index: Int): Movie {
        val movie = Movie()
        val cursor = database!!.rawQuery("SELECT " +
                MoviesTable.COLUMN_TITLE +
                "," +
                MoviesTable.COLUMN_DESCRIPTION +
                "," +
                MoviesTable.COLUMN_DATE +
                "," +
                MoviesTable.COLUMN_URL +
                " FROM " + MoviesTable.TABLE_NAME + " WHERE cod=" + index, null)
        cursor.moveToFirst()
        movie.title = cursor.getString(0)
        movie.subtitle = cursor.getString(1)
        movie.date = cursor.getString(2)
        movie.url = cursor.getString(3)
        cursor.close()
        return movie
    }

    fun clearMovies() {
        database!!.execSQL("DELETE FROM " + MoviesTable.TABLE_NAME)
    }

}
