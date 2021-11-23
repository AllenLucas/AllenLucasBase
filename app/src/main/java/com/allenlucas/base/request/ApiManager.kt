package com.allenlucas.base.request

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络请求管理
 */
class ApiManager private constructor() {

    companion object {
        val instance by lazy { ApiManager() }
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}