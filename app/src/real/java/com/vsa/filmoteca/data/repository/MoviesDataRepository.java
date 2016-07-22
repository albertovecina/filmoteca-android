package com.vsa.filmoteca.data.repository;

import com.vsa.filmoteca.data.domain.Movie;
import com.vsa.filmoteca.data.domain.mapper.DetailHtmlParser;
import com.vsa.filmoteca.data.domain.mapper.MovieHtmlMapper;
import com.vsa.filmoteca.data.repository.ws.CacheRequestInterceptor;
import com.vsa.filmoteca.data.repository.ws.WSClient;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public class MoviesDataRepository {

    private static MoviesDataRepository sRepository;

    public Observable<List<Movie>> moviesList() {
        return WSClient.getClient(CacheRequestInterceptor.CachePolicy.PRIORITY_NETWORK).moviesListHtml().map(MovieHtmlMapper::transformMovie)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> movieDetail(String url) {
        return WSClient.getClient(CacheRequestInterceptor.CachePolicy.PRIORITY_NETWORK).movieDetail(url).map(html -> {
            try {
                return DetailHtmlParser.parse(html);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
