package com.vsa.filmoteca.presenter;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vsa.filmoteca.view.webview.ObservableWebView;

/**
 * Created by seldon on 13/03/15.
 */
public interface DetailPresenter extends SwipeRefreshLayout.OnRefreshListener {

    void onCreate(String title);

    boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu);

    boolean onOptionsItemSelected(MenuItem item);

    void onNewIntent(Intent intent);

    void onFabClick();

}
