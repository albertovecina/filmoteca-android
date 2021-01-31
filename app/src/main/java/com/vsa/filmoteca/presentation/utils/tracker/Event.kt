package com.vsa.filmoteca.presentation.utils.tracker

sealed class Event(val value: String) {
    object ReviewLaunched : Event("app_review_launched")
}