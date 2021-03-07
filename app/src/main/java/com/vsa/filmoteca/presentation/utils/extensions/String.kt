package com.vsa.filmoteca.presentation.utils.extensions

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String?.toUrlEncoded(): String? = URLEncoder.encode(this, StandardCharsets.UTF_8.name())