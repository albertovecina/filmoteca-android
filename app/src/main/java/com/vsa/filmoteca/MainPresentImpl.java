package com.vsa.filmoteca;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vsa.filmoteca.dialog.interfaces.OkCancelDialogListener;
import com.vsa.filmoteca.utils.Constants;
import com.vsa.filmoteca.utils.NetworkUtils;
import com.vsa.filmoteca.utils.PageParser;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.List;

/**
 * Created by seldon on 10/03/15.
 */

public class MainPresentImpl extends AsyncHttpResponseHandler implements MainPresenter, OkCancelDialogListener {

    private MainView mMainView;

    private List<HashMap<String, String>> mMoviesList;

    public MainPresentImpl(MainView mainView){
        mMainView = mainView;
    }

    @Override
    public void onMovieClicked(int position) {
        mMainView.showDetail(mMoviesList.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.refresh:
                loadMovies();
                break;
            case R.id.acercade:
                mMainView.showAboutUs();
                return true;
        }
        return false;
    }

    @Override
    public void onResume(Intent intent) {
        HashMap<String,String> movie = (HashMap<String, String>) intent.getSerializableExtra(MainActivity.EXTRA_MOVIE);
        if(movie != null) {
            intent.removeExtra(MainActivity.EXTRA_MOVIE);
            mMainView.showDetail(movie);
        } else {
            if (!mMainView.isListLoaded()) {
                if (!NetworkUtils.isNetworkAvailable(mMainView.getContext())) {
                    mMainView.showWifiRequestDialog(this);
                } else {
                    loadMovies();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void loadMovies() {
        mMainView.stopRefreshing();
        mMainView.showProgressDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constants.TIMEOUT_APP);
        client.get(Constants.URL_SOURCE, this);
    }

    @Override
    public Context getContext() {
        return mMainView.getContext();
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String html = new String(responseBody);
        mMoviesList = PageParser.parseMoviesList(html);
        mMainView.showTitle(mMoviesList.size());
        if(mMoviesList.size() < 1)
            mMainView.showNoEventsDialog();
        else
            mMainView.setMovies(mMoviesList);
        mMainView.showChangeLog();
        mMainView.hideProgressDialog();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        mMainView.showTimeOutDialog();
    }

    @Override
    public void onAcceptButtonPressed() {
        mMainView.showWifiSettings();
    }

    @Override
    public void onCancelButtonPressed() {
        mMainView.finish();
    }

    @Override
    public void onRefresh() {
        loadMovies();
    }
}
