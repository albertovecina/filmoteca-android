package com.vsa.filmoteca.repository;

import com.vsa.filmoteca.model.domain.Movie;
import com.vsa.filmoteca.model.domain.mapper.DetailHtmlParser;
import com.vsa.filmoteca.model.domain.mapper.MovieHtmlMapper;
import com.vsa.filmoteca.repository.ws.WSClient;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public class DataRepository {

    private static DataRepository sRepository;

    public static DataRepository getInstance() {
        if (sRepository == null)
            sRepository = new DataRepository();
        return sRepository;
    }

    public Observable<List<Movie>> moviesList() {
        return WSClient.getClient().moviesListHtml().map(MovieHtmlMapper::transformMovie)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> movieDetail(String url) {
        return WSClient.getClient().movieDetail(url).map(html -> {
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
