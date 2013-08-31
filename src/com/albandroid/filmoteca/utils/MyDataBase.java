package com.albandroid.filmoteca.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBase extends SQLiteOpenHelper{
	//Sentencia SQL para crear la tabla de Usuarios
	String sqlCreate = "CREATE TABLE Peliculas (cod INTEGER, "+
			Constants.PARAM_ID_TITULO+" TEXT, "+
			Constants.PARAM_ID_SUBTITULO+" TEXT, "+
			Constants.PARAM_ID_DESCRIPCION+" TEXT, "+
			Constants.PARAM_ID_FECHA+" TEXT, "+
			Constants.PARAM_ID_URL+" TEXT)";
	
	public MyDataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
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
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
 
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
	}

}
