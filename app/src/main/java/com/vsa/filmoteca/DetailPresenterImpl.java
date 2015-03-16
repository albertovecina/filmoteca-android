package com.vsa.filmoteca;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vsa.filmoteca.utils.Constants;
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
        inflater.inflate(R.menu.sharewith, menu);
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
        String html = parseHTML(new String(responseBody));
        if(html == null) {
            mDetailView.showTimeOutDialog();
        } else {
            mDetailView.setWebViewContent(html);
        }
        mDetailView.hideProgressDialog();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        mDetailView.showTimeOutDialog();
        //Probably this error comes from an inconsistent widget data. We must to update
        //the widget information to match the entries for the next time.
        updateWidget();
    }

    private String parseHTML(String html){
        String res;
        if(html==null){
            return html;
        }
        //Style
        String style="<style type=\"text/css\">img{ max-width:100%!important; height:auto!important;} strong{font-size:13px;} " +
                //"*{background-color:#f3f3f3!important;}"+
                "a{font-size:15px!important;}"+
                "p{text-align:center;}"+
                ".documentDescription{font-weight:bold;color:#000000; text-align:center;}"+
                ".tablaeventos table{ width:100%!important;}"+
                ".tablaeventos td{width:50%!important;}"+
                "a {color:#000000; font-weight:bold;}"+
                ".vevent{font-size:15px!important; color:#5f5c5c;}"+
                "td{ font-size:15px!important;line-height:18px; vertical-align:top;}"+
                "table{border:1px solid #848484;}"+
                "th{float:left!important;font-size:13px!important;}</style>";

        //Parseando Info
        html=html.substring(html.indexOf("<div class=\"vevent\">"));
        res=style+html.substring(0,html.indexOf("<div class=\"relatedItems\">"));
        res=res.replaceAll("\\<th", "<td");
        res=res.replaceAll("<\\/th", "</td");
        return res;
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
