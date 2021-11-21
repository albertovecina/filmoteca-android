package com.vsa.filmoteca.data.source.ws


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import rx.Observable

/**
 * Created by albertovecinasanchez on 8/3/16.
 */
interface FilmotecaInterface {

    @GET("es/webs-municipales/filmoteca/agenda/folder_listing")
    fun moviesListHtml(): Observable<String>

    @GET
    fun movieDetail(@Url url: String): Call<String>

}
