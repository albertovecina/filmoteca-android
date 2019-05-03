package com.vsa.filmoteca.data.repository.ws;


import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by albertovecinasanchez on 8/3/16.
 */
public interface FilmotecaInterface {

    @GET("/es/webs-municipales/filmoteca/agenda/folder_listing")
    Observable<String> moviesListHtml();

    @GET
    Observable<String> movieDetail(@Url String url);

}
