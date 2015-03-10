package com.vsa.filmoteca;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vsa.filmoteca.dialog.interfaces.OkCancelDialogListener;
import com.vsa.filmoteca.utils.Constants;
import com.vsa.filmoteca.utils.NetworkUtils;

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
            case R.id.acercade:
                mMainView.showAboutUs();
                return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        if(!mMainView.isListLoaded()){
            if(!NetworkUtils.isNetworkAvailable(mMainView.getContext())){
                mMainView.showWifiRequestDialog(this);
            }else{
                loadMovies();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.acercade, menu);
        return true;
    }

    @Override
    public void loadMovies() {
        mMainView.showProgressDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constants.URL_SOURCE, this);
    }

    @Override
    public Context getContext() {
        return mMainView.getContext();
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String html = new String(responseBody);
        mMoviesList = NetworkUtils.parseMoviesList(html);
        mMainView.setMovies(mMoviesList);
        mMainView.hideProgressDialog();
        mMainView.showChangeLog();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

    }

    @Override
    public void onAcceptButtonPressed() {
        mMainView.showWifiSettings();
    }

    @Override
    public void onCancelButtonPressed() {
        mMainView.finish();
    }
}
