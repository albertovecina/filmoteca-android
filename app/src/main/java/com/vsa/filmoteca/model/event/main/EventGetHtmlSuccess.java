package com.vsa.filmoteca.model.event.main;

/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public class EventGetHtmlSuccess {

    private String html;

    public EventGetHtmlSuccess(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

}
