package com.vsa.filmoteca.presenter;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vsa.filmoteca.R;
import com.vsa.filmoteca.model.DetailInfoParser;
import com.vsa.filmoteca.presenter.utils.StringUtils;
import com.vsa.filmoteca.view.DetailView;
import com.vsa.filmoteca.view.activity.DetailActivity;
import com.vsa.filmoteca.view.widget.EventsWidget;

import org.apache.http.Header;

/**
 * Created by seldon on 13/03/15.
 */
public class DetailPresenterImpl extends AsyncHttpResponseHandler implements DetailPresenter {

    private String mContentUrl;
    private String mTitle;

    private DetailView mView;

    public DetailPresenterImpl(DetailView detailView) {
        mView = detailView;
    }

    @Override
    public void onCreate(String title) {
        mTitle = title.trim();
    }

    @Override
    public boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                mView.showShareDialog();
                return true;
            case R.id.menu_item_browser:
                mView.launchBrouser(mContentUrl);
                return true;
            case R.id.menu_item_filmaffinity:
                launchFilmaffinitySearch();
                return true;
            case R.id.menu_item_refresh:
                onRefresh();
                return true;
            case R.id.menu_item_about_us:
                mView.showAboutUs();
                return true;
            case android.R.id.home:
                mView.onBackPressed();
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onNewIntent(Intent intent) {
        String url = intent.getStringExtra(DetailActivity.EXTRA_URL);
        if (url != null && !url.isEmpty()) {
            mTitle = intent.getStringExtra(DetailActivity.EXTRA_TITLE);
            mView.setWebViewContent("<html></html>", url);
            mView.showMovieTitle(mTitle);
            loadContent(url);
        }
    }

    public void onFabClick() {
        mView.navitgateToComments(mTitle);
    }

    public void loadContent(String url) {
        mContentUrl = url;
        mView.stopRefreshing();
        mView.showProgressDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(mContentUrl, this);
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String html;
        try {
            html = DetailInfoParser.parse(new String(responseBody));
        } catch (Exception e) {
            e.printStackTrace();
            html = "";
        }
        if (html == null)
            mView.showTimeOutDialog();
        else
            mView.setWebViewContent(html, mContentUrl);
        mView.hideProgressDialog();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        mView.showTimeOutDialog();
        //Probably this error comes from an inconsistent widget data. We must to update
        //the widget information to match the entries for the next time.
        updateWidget();
    }

    @Override
    public void onRefresh() {
        if (mContentUrl != null && !mContentUrl.isEmpty())
            loadContent(mContentUrl);
    }

    private void updateWidget() {
        Intent intent = new Intent(mView.getContext(), EventsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = {R.xml.appwidget_info};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        mView.getContext().sendBroadcast(intent);
    }

    private void launchFilmaffinitySearch() {
        String searchString = mTitle.replace(" ", "+");
        searchString = StringUtils.removeAccents(searchString);
        String url = "http://m.filmaffinity.com/es/search.php?stext=" + searchString + "&stype=title";
        mView.launchBrouser(url);
    }

}

