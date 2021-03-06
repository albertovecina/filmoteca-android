package com.vsa.filmoteca.presentation.tracker

sealed class Event(val name: String) {

    companion object {
        const val PARAM_MOVIE_TITLE = "movie_title"
    }

    object AppReviewRequest : Event("app_review_request")

    object ClickAboutUs : Event("click_about_us")

    object ClickMovieItem : Event("click_movie_item")

    object ClickFilmAffinity : Event("click_film_affinity")

    object ClickShareMovie : Event("click_share_movie")

    object ClickWebSite : Event("click_website")

}