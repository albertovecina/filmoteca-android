package com.vsa.filmoteca;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by seldon on 10/03/15.
 */
public interface MainPresenter {

    public void onMovieClicked(int position);

    public boolean onOptionsItemSelected(MenuItem item);

    public void onResume();

    public boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu);

    public void loadMovies();

    public Context getContext();

}
