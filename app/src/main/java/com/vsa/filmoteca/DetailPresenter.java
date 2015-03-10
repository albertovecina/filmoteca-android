package com.vsa.filmoteca;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by seldon on 13/03/15.
 */
public interface DetailPresenter {
    public boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu);
    public boolean onOptionsItemSelected(MenuItem item);
    public void loadContent(String url);
}
