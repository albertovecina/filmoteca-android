package com.vsa.filmoteca.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void insertMovie(int index, HashMap<String, String> movie){
        mDatabase.execSQL("INSERT INTO " + WidgetDataBaseHelper.TABLE_MOVIES +
                " VALUES ("+Integer.toString(index)+",'"+
                movie.get(Constants.PARAM_ID_TITULO)+"','Subtitulo','"+
                movie.get(Constants.PARAM_ID_DESCRIPCION)+"','"+
                movie.get(Constants.PARAM_ID_FECHA)+"','"+
                movie.get(Constants.PARAM_ID_URL)+"')");
    }

    public HashMap<String, String> getMovie(int index){
        HashMap<String, String> movie = new HashMap<String, String>();
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
        movie.put(Constants.PARAM_ID_TITULO, cursor.getString(0));
        movie.put(Constants.PARAM_ID_DESCRIPCION, cursor.getString(1));
        movie.put(Constants.PARAM_ID_FECHA, cursor.getString(2));
        movie.put(Constants.PARAM_ID_URL, cursor.getString(3));
        cursor.close();
        return movie;
    }

    public void clearMovies(){
        mDatabase.execSQL("DELETE FROM " + MoviesTable.TABLE_NAME);
    }

}
