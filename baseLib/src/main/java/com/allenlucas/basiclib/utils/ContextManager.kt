package com.allenlucas.basiclib.utils

import android.content.Context
import kotlin.properties.Delegates

class ContextManager private constructor() {

    companion object {
        val instance by lazy { ContextManager() }
    }

    private var mContext by Delegates.notNull<Context>()

    fun initContext(context: Context) {
        mContext = context
    }

    fun getAppContext() = mContext.applicationContext
}