package com.vsa.filmoteca.model.domain;

import com.vsa.filmoteca.view.adapter.EventsAdapter;

import java.io.Serializable;

/**
 * Created by seldon on 26/03/15.
 */
public class Movie implements EventsAdapter.Event, Serializable {

    private String mTitle;
    private String mSubtitle;
    private String mDate;
    private String mUrl;

    public Movie() {
        mTitle = "";
        mSubtitle = "";
        mDate = "";
        mUrl = "";
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String mSubtitle) {
        this.mSubtitle = mSubtitle;
    }

    @Override
    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

}
