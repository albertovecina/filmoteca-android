package com.vsa.filmoteca.domain.model

import java.io.Serializable

/**
 * Created by seldon on 28/05/21.
 */
data class Movie(
        var title: String = "",
        var subtitle: String = "",
        var place: String? = null,
        var date: String = "",
        var url: String = ""
) : Serializable
