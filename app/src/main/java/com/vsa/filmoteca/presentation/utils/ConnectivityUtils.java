package com.vsa.filmoteca.presentation.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.vsa.filmoteca.FilmotecaApplication;

public class ConnectivityUtils {

    public static boolean isInternetAvailable(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null, otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean isInternetAvailable() {
        return isInternetAvailable(FilmotecaApplication.getInstance());
    }

}
