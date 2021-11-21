package com.vsa.filmoteca.data.net

interface AsyncExecutor {

    fun <R> execute(block: () -> R, callback: ((R) -> Unit)? = null)

}