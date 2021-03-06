package com.vsa.filmoteca.presentation.tracker

interface Tracker {

    fun logAppReviewRequest()

    fun logClickAboutUs()

    fun logClickMovieItem(title:String)

    fun logClickFilmAffinity(title:String)

    fun logClickWebsite(title:String)

    fun logClickShareMovie(title:String)

}