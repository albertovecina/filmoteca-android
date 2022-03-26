package com.vsa.filmoteca.presentation.view.adapter.model

import com.vsa.filmoteca.domain.model.Movie
import java.io.Serializable

data class MovieViewModel(
        var title: String = "",
        var subtitle: String = "",
        var place: String? = null,
        var date: String = "",
        var url: String = ""
) : Serializable

fun Movie?.toViewModel() = this?.let {
    MovieViewModel(title, subtitle, place, "- $date", url)
} ?: MovieViewModel()

fun List<Movie>?.toViewModel() = this?.map {
    it.toViewModel()
} ?: emptyList()