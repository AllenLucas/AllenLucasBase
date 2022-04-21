package com.allenlucas.basiclib.base

import com.allenlucas.basiclib.request.BaseApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository<T : BaseApi> {

    protected val api by lazy { getService() }

    abstract fun getService(): T

    suspend inline fun baseRequest(
        crossinline success: suspend CoroutineScope.() -> Unit,
        crossinline error: suspend CoroutineScope.(Throwable) -> Unit,
        crossinline complete: suspend CoroutineScope.() -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                success()
            } catch (e: Exception) {
                error(e)
            } finally {
                complete()
            }
        }
    }
}