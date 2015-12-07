package com.vsa.filmoteca.repository;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vsa.filmoteca.presenter.utils.Constants;

/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public class DataRepository {

    private AsyncHttpClient mHttpClient;
    private static DataRepository sRepository;

    private DataRepository() {
        super();
        mHttpClient = new AsyncHttpClient();
    }

    public static DataRepository getInstance() {
        if (sRepository == null)
            sRepository = new DataRepository();
        return sRepository;
    }

    public void requestHtml(String url, AsyncHttpResponseHandler responseHandler) {
        mHttpClient.setTimeout(Constants.TIMEOUT_APP);
        mHttpClient.get(url, responseHandler);
    }

}
