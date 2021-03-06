package com.vsa.filmoteca.presentation.tracker

sealed class Event(val value: String) {

    object AppReviewRequest : Event("app_review_request")

}