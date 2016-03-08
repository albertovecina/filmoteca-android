package com.vsa.filmoteca.repository.sharedpreferences;

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
    private static final String SHAREDPREFERENCES_TWITTER_PROFILE_IMAGE_URL = "TwitterProfileImageUrl";
    private static final String SHAREDPREFERENCES_TWITTER_USER_DESCRIPTION = "TwitterUserDescription";


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

    public static String getTwitterProfileImageUrl(Context context) {
        initSharedPreferences(context);
        return mSharedPreferences.getString(SHAREDPREFERENCES_TWITTER_PROFILE_IMAGE_URL, "");
    }

    public static void setTwitterProfileImageUrl(Context context, String url) {
        initSharedPreferences(context);
        mSharedPreferences
                .edit()
                .putString(SHAREDPREFERENCES_TWITTER_PROFILE_IMAGE_URL, url)
                .commit();
    }

    public static String getTwitterUserDescription(Context context) {
        initSharedPreferences(context);
        return mSharedPreferences.getString(SHAREDPREFERENCES_TWITTER_USER_DESCRIPTION, "");
    }

    public static void setTwitterUserDescription(Context context, String userDescription) {
        initSharedPreferences(context);
        mSharedPreferences
                .edit()
                .putString(SHAREDPREFERENCES_TWITTER_USER_DESCRIPTION, userDescription)
                .commit();
    }

    public static void removeTwitterAccountInfo(Context context) {
        mSharedPreferences
                .edit()
                .remove(SHAREDPREFERENCES_TWITTER_PROFILE_IMAGE_URL)
                .remove(SHAREDPREFERENCES_TWITTER_USER_DESCRIPTION)
                .commit();
    }


}
