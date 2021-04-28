package com.vsa.filmoteca.presentation.view.adapter.model

import com.vsa.filmoteca.domain.model.Movie
import java.io.Serializable

data class MovieViewModel(
        var title: String = "",
        var subtitle: String = "",
        var date: String = "",
        var url: String = ""
) : Serializable

fun Movie?.toViewModel() = this?.let {
    MovieViewModel(title, subtitle, date, url)
} ?: MovieViewModel()

fun List<Movie>?.toViewModel() = this?.map {
    it.toViewModel()
} ?: emptyList()