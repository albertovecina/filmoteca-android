package com.vsa.filmoteca.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.vsa.filmoteca.utils.Constants;
import com.vsa.filmoteca.utils.StringUtils;

import butterknife.OnClick;

/**
 * Created by seldon on 13/03/15.
 */
public interface DetailView {
    public void initViews();
    public void showProgressDialog();
    public void hideProgressDialog();
    public void showMovieTitle(String title);
    public void stopRefreshing();
    public void showTimeOutDialog();
    public void showInFilmAffinity();
    public void showInBrowser();
    public void showShareDialog();
    public void showAboutUs();
    public void onBackPressed();
    public void setWebViewContent(String html);
    public Context getContext();
    public void finish();
}
