package com.vsa.filmoteca.data.net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoroutineAsyncExecutor @Inject constructor() : AsyncExecutor {

    override fun <R> execute(block: () -> R, callback: ((R) -> Unit)?) {
        MainScope().launch {
            val result = withContext(Dispatchers.IO) {
                block()
            }
            callback?.invoke(result)
        }
    }

}