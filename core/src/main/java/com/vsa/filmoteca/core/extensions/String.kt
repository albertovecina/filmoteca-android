package com.vsa.filmoteca.core.extensions

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String?.toUrlEncoded(): String? = URLEncoder.encode(this, StandardCharsets.UTF_8.name())