package com.vsa.filmoteca.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by seldon on 10/03/15.
 */
public interface MainPresenter extends SwipeRefreshLayout.OnRefreshListener{

    public void onMovieClicked(int position);

    public boolean onOptionsItemSelected(MenuItem item);

    public void onResume(Intent intent);

    public boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu);

    public void loadMovies();

    public Context getContext();

}
