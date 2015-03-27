package com.vsa.filmoteca.model.database;

import android.provider.BaseColumns;

import com.vsa.filmoteca.utils.Constants;

/**
 * Created by seldon on 16/03/15.
 */
public class MoviesTable implements BaseColumns{

    public static final String TABLE_NAME = "movies";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SUBTITLE = "subtitle";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_URL = "url";

}
