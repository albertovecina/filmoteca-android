package com.vsa.filmoteca.interactor;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vsa.filmoteca.model.MoviesFactory;
import com.vsa.filmoteca.model.event.BUS;
import com.vsa.filmoteca.model.event.main.EventGetHtmlError;
import com.vsa.filmoteca.model.event.main.EventGetHtmlSuccess;
import com.vsa.filmoteca.presenter.utils.NetworkUtils;
import com.vsa.filmoteca.repository.DataRepository;

import cz.msebera.android.httpclient.Header;


/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public class FilmotecaInteractorImpl implements FilmotecaInteractor {

    private DataRepository mRepository = DataRepository.getInstance();

    private AsyncHttpResponseHandler mMoviesResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String htmlResponse = new String(responseBody);
            BUS.getInstance()
                    .post(new EventGetHtmlSuccess(htmlResponse));
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            BUS.getInstance().post(new EventGetHtmlError());
        }
    };


    @Override
    public void getHtml(String url) {
        mRepository.requestHtml(url, mMoviesResponseHandler);
    }

    @Override
    public boolean isNetworkAvailable() {
        return NetworkUtils.isNetworkAvailable();
    }

}
