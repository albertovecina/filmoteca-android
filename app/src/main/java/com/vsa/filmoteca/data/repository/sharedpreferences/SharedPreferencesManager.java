package com.vsa.filmoteca.data.repository.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

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


    private SharedPreferences mSharedPreferences;

    @Inject
    public SharedPreferencesManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public int getCurrentMovieIndex() {
        return mSharedPreferences.getInt(SHAREDPREFERENCES_CURRENT, 0);
    }

    public void setCurrentMovieIndex(int index) {
        mSharedPreferences
                .edit()
                .putInt(SHAREDPREFERENCES_CURRENT, index)
                .commit();
    }

    public int getMoviesCount() {
        return mSharedPreferences.getInt(SHAREDPREFERENCES_SIZE, 0);
    }

    public void setMoviesCount(int index) {
        mSharedPreferences
                .edit()
                .putInt(SHAREDPREFERENCES_SIZE, index)
                .commit();
    }

    public String getTwitterProfileImageUrl() {
        return mSharedPreferences.getString(SHAREDPREFERENCES_TWITTER_PROFILE_IMAGE_URL, "");
    }

    public void setTwitterProfileImageUrl(String url) {
        mSharedPreferences
                .edit()
                .putString(SHAREDPREFERENCES_TWITTER_PROFILE_IMAGE_URL, url)
                .commit();
    }

    public String getTwitterUserDescription() {
        return mSharedPreferences.getString(SHAREDPREFERENCES_TWITTER_USER_DESCRIPTION, "");
    }

    public void setTwitterUserDescription(String userDescription) {
        mSharedPreferences
                .edit()
                .putString(SHAREDPREFERENCES_TWITTER_USER_DESCRIPTION, userDescription)
                .commit();
    }

    public void removeTwitterAccountInfo() {
        mSharedPreferences
                .edit()
                .remove(SHAREDPREFERENCES_TWITTER_PROFILE_IMAGE_URL)
                .remove(SHAREDPREFERENCES_TWITTER_USER_DESCRIPTION)
                .commit();
    }


}
