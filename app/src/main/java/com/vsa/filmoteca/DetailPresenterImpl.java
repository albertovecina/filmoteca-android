package com.vsa.filmoteca;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by seldon on 13/03/15.
 */
public class DetailPresenterImpl extends AsyncHttpResponseHandler implements DetailPresenter{

    DetailView mDetailView;

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
            default:
                return false;
        }
    }

    @Override
    public void loadContent(String url) {
        mDetailView.showProgressDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, this);

    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String html = parseHTML(new String(responseBody));
        if(html == null) {
            mDetailView.finish();
            mDetailView.showTimeOutDialog();
        } else {
            mDetailView.setWebViewContent(html);
        }
        mDetailView.hideProgressDialog();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        mDetailView.finish();
        mDetailView.showTimeOutDialog();
    }

    private String parseHTML(String html){
        String res;
        if(html==null){
            return html;
        }
        //Style
        String style="<style type=\"text/css\">img{ max-width:100%!important; height:auto!important;} strong{font-size:13px;} " +
                "*{background-color:#f3f3f3!important;}"+
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
}
