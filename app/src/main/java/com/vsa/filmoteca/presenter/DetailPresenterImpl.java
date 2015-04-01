package com.vsa.filmoteca.presenter;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vsa.filmoteca.R;
import com.vsa.filmoteca.view.DetailView;
import com.vsa.filmoteca.view.activity.CommentsActivity;
import com.vsa.filmoteca.view.activity.DetailActivity;
import com.vsa.filmoteca.view.widget.EventsWidget;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seldon on 13/03/15.
 */
public class DetailPresenterImpl extends AsyncHttpResponseHandler implements DetailPresenter{

    private DetailView mView;
    private String mCurrentUrl;

    private String mTitle;

    public DetailPresenterImpl(DetailView detailView){
        mView = detailView;
    }

    private static final String CLASS_TABLAEVENTOS = "tablaeventos";
    private static final String CLASS_VEVENT = "vevent";

    @Override
    public boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_share:
                mView.showShareDialog();
                return true;
            case R.id.menu_item_browser:
                mView.showInBrowser();
                return true;
            case R.id.menu_item_filmaffinity:
                mView.showInFilmAffinity();
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
        if(url != null && !url.isEmpty()) {
            mTitle = intent.getStringExtra(DetailActivity.EXTRA_TITLE);
            mView.setWebViewContent("<html></html>");
            mView.showMovieTitle(mTitle);
            loadContent(url);
        }
    }

    public void onFabClick() {
        mView.navitgateToComments(mTitle);
    }

    public void loadContent(String url) {
        mCurrentUrl = url;
        mView.stopRefreshing();
        mView.showProgressDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(mCurrentUrl, this);
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String html = parseDetailPage(new String(responseBody));
        if(html == null)
            mView.showTimeOutDialog();
        else
            mView.setWebViewContent(html);
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
        if(mCurrentUrl != null && !mCurrentUrl.isEmpty())
            loadContent(mCurrentUrl);
    }

    private void updateWidget(){
        Intent intent = new Intent(mView.getContext(), EventsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = {R.xml.appwidget_info};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        mView.getContext().sendBroadcast(intent);
    }

    private String parseDetailPage(String html){

        if(html==null){
            return html;
        }
        //Style
        String style="<style type=\"text/css\">img{ max-width:100%!important; height:auto!important;} strong{font-size:13px;} " +
                "a{font-size:15px!important;" +
                    "word-wrap: break-word; /* Internet Explorer 5.5+ */ "+
                "}"+
                "p{text-align:center;}"+
                ".documentDescription{font-weight:bold;color:#000000; text-align:center;}"+
                ".tablaeventos table{ width:100%!important;}"+
                ".tablaeventos td{width:50%!important;}"+
                "a {color:#000000; font-weight:bold;}"+
                ".vevent{font-size:15px!important; color:#5f5c5c;}"+
                "td{ font-size:15px!important;line-height:18px; vertical-align:top;}"+
                "table{border:1px solid #848484;}"+
                "dd{ margin: 8px 5px 8px 105px;" +
                    "color: rgb(51, 51, 51); " +
                    "font-size: 14px; line-height: 18px; font-family: Arial, sans-serif; font-style: normal;" +
                    "font-variant: normal; font-weight: normal; letter-spacing: normal; " +
                    "orphans: auto; text-align: start; text-indent: 0px; text-transform: none; " +
                    "white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; " +
                    "background-color: rgb(255, 255, 255); " +
                "}" +
                "th{float:left!important;font-size:13px!important;}</style>";

        Document document = Jsoup.parse(html);
        document.getElementsByTag("dd").removeAttr("style");
        document.getElementsByTag("a").removeAttr("href");

        Element vevent = document.getElementsByClass(CLASS_VEVENT).first();

        if(vevent.getElementsByClass(CLASS_TABLAEVENTOS).isEmpty()) {
            Element tablaeventos = document.getElementsByClass(CLASS_TABLAEVENTOS).first();
            if(tablaeventos != null) {
                List<Node> nodesToInsert = new ArrayList<Node>();
                nodesToInsert.add((Node) tablaeventos);
                vevent = vevent.insertChildren(vevent.childNodeSize(), nodesToInsert);
            }
        }

        return style + vevent.html();
    }
}

