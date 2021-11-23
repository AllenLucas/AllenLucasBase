package com.allenlucas.base.base

import com.allenlucas.base.request.ApiManager
import com.allenlucas.base.request.IApi
import com.allenlucas.basiclib.base.BaseRepository
import kotlinx.coroutines.CoroutineScope

open class AppBaseRepository : BaseRepository<IApi>() {

    override fun getService() = ApiManager.instance.create(IApi::class.java)

    suspend inline fun request(
        crossinline call: suspend CoroutineScope.() -> Unit
    ) {
        baseRequest({ call() }, {}, {})
    }
}