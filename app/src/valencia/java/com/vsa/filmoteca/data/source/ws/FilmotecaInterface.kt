package com.vsa.filmoteca.data.source.ws


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by albertovecinasanchez on 8/3/16.
 */
interface FilmotecaInterface {

    @GET("val/audiovisual/programacio/programacio-dies")
    fun moviesListHtml(): Call<String>

    @GET
    fun movieDetail(@Url url: String): Call<String>

}
