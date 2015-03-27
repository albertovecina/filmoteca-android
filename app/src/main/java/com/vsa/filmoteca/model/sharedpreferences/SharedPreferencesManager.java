package com.vsa.filmoteca.model.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by seldon on 27/03/15.
 */
public class SharedPreferencesManager {

    //SharedPreferences
    private static final String SHAREDPREFERENCES_NAME = "MySharedPreferences";
    private static final String SHAREDPREFERENCES_CURRENT = "Current";
    private static final String SHAREDPREFERENCES_SIZE = "Size";

    private static SharedPreferences mSharedPreferences;

    private static void initSharedPreferences(Context context) {
        if(mSharedPreferences == null)
            mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static int getCurrentMovieIndex(Context context) {
        initSharedPreferences(context);
        return mSharedPreferences.getInt(SHAREDPREFERENCES_CURRENT, 0);
    }

    public static void setCurrentMovieIndex(Context context, int index) {
        initSharedPreferences(context);
        mSharedPreferences
                .edit()
                .putInt(SHAREDPREFERENCES_CURRENT, index)
                .commit();
    }

    public static int getMoviesCount(Context context) {
        initSharedPreferences(context);
        return mSharedPreferences.getInt(SHAREDPREFERENCES_SIZE, 0);
    }

    public static void setMoviesCount(Context context, int index) {
        initSharedPreferences(context);
        mSharedPreferences
                .edit()
                .putInt(SHAREDPREFERENCES_SIZE, index)
                .commit();
    }

}
