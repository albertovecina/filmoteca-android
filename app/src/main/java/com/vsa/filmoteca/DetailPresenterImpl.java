package com.vsa.filmoteca;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vsa.filmoteca.utils.PageParser;
import com.vsa.filmoteca.widget.EventsWidget;

import org.apache.http.Header;

/**
 * Created by seldon on 13/03/15.
 */
public class DetailPresenterImpl extends AsyncHttpResponseHandler implements DetailPresenter{

    private DetailView mDetailView;

    private String mCurrentUrl;

    public DetailPresenterImpl(DetailView detailView){
        mDetailView = detailView;
    }

    @Override
    public boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.compartir:
                mDetailView.showShareDialog();
                return true;
            case R.id.navegar:
                mDetailView.showInBrowser();
                return true;
            case R.id.filmaffinity:
                mDetailView.showInFilmAffinity();
                return true;
            case R.id.refresh:
                onRefresh();
                return true;
            case R.id.acercade:
                mDetailView.showAboutUs();
                return true;
            case android.R.id.home:
                mDetailView.onBackPressed();
            default:
                return false;
        }
    }

    @Override
    public void loadContent(String url) {
        mCurrentUrl = url;
        mDetailView.stopRefreshing();
        mDetailView.showProgressDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(mCurrentUrl, this);
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String html = PageParser.parseDetailPage(new String(responseBody));
        if(html == null)
            mDetailView.showTimeOutDialog();
        else
            mDetailView.setWebViewContent(html);
        mDetailView.hideProgressDialog();
    }

    @Override
    public void onNewIntent(Intent intent) {
        mDetailView.setWebViewContent("<html></html>");
        mDetailView.showMovieTitle(intent.getStringExtra(DetailActivity.EXTRA_TITLE)
                .substring(1));
        loadContent(intent.getStringExtra(DetailActivity.EXTRA_URL));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        mDetailView.showTimeOutDialog();
        //Probably this error comes from an inconsistent widget data. We must to update
        //the widget information to match the entries for the next time.
        updateWidget();
    }

    @Override
    public void onRefresh() {
        if(mCurrentUrl != null && !mCurrentUrl.isEmpty())
            loadContent(mCurrentUrl);
    }

    private void updateWidget(){
        Intent intent = new Intent(mDetailView.getContext(), EventsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = {R.xml.appwidget_info};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        mDetailView.getContext().sendBroadcast(intent);
    }

}
