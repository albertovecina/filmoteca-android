package com.vsa.filmoteca.domain.model

import java.io.Serializable

/**
 * Created by seldon on 26/03/15.
 */
data class Movie(
        var title: String = "",
        var subtitle: String = "",
        var date: String = "",
        var url: String = ""
) : Serializable
