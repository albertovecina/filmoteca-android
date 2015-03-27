package com.vsa.filmoteca.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.vsa.filmoteca.model.Movie;

import com.vsa.filmoteca.utils.Constants;

import java.util.HashMap;

/**
 * Created by seldon on 16/03/15.
 */
public class WidgetDataSource {

    private SQLiteDatabase mDatabase;
    private WidgetDataBaseHelper mDatabaseHelper;


    public WidgetDataSource(Context context) {
        mDatabaseHelper = new WidgetDataBaseHelper(context);
    }

    public void open() {
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public void insertMovie(int index, Movie movie){
        mDatabase.execSQL("INSERT INTO " + WidgetDataBaseHelper.TABLE_MOVIES +
                " VALUES (" +
                Integer.toString(index) + ",'" +
                movie.getTitle() + "','" +
                movie.getSubtitle() + "','" +
                "description','" +
                movie.getDate() + "','" +
                movie.getUrl() + "')");
    }

    public Movie getMovie(int index){
        Movie movie = new Movie();
        Cursor cursor=mDatabase.rawQuery("SELECT "+
                MoviesTable.COLUMN_TITLE +
                "," +
                MoviesTable.COLUMN_DESCRIPTION +
                "," +
                MoviesTable.COLUMN_DATE +
                "," +
                MoviesTable.COLUMN_URL +
                " FROM " + MoviesTable.TABLE_NAME + " WHERE cod="+index, null);
        cursor.moveToFirst();
        movie.setTitle(cursor.getString(0));
        movie.setSubtitle(cursor.getString(1));
        movie.setDate(cursor.getString(2));
        movie.setUrl(cursor.getString(3));
        cursor.close();
        return movie;
    }

    public void clearMovies(){
        mDatabase.execSQL("DELETE FROM " + MoviesTable.TABLE_NAME);
    }

}
