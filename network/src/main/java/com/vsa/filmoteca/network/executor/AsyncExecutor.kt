package com.vsa.filmoteca.network.executor

interface AsyncExecutor {

    fun <R> execute(block: () -> R, callback: ((R) -> Unit)? = null)

}