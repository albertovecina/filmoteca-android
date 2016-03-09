package com.vsa.filmoteca.data.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WidgetDataBaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "WidgetDatabase.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_MOVIES = "movies";

	//Sentencia SQL para crear la tabla de Usuarios
	String sqlCreate = "CREATE TABLE " + MoviesTable.TABLE_NAME + " (cod INTEGER, " +
            MoviesTable.COLUMN_TITLE + " TEXT, " +
			MoviesTable.COLUMN_SUBTITLE + " TEXT, " +
            MoviesTable.COLUMN_DESCRIPTION + " TEXT, " +
            MoviesTable.COLUMN_DATE + " TEXT, " +
            MoviesTable.COLUMN_URL + " TEXT)";

    public WidgetDataBaseHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	public WidgetDataBaseHelper(Context context, String name, CursorFactory factory,
                                int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		//Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
 
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
	}

}
