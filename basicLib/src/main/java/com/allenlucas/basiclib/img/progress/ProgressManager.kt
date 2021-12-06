package com.allenlucas.basiclib.img.progress

import android.text.TextUtils
import com.allenlucas.basiclib.img.OnProgressListener
import okhttp3.OkHttpClient
import java.util.concurrent.ConcurrentHashMap

/**
 * 进度监听器管理类
 * 加入图片加载进度监听，加入Https支持
 */
object ProgressManager {

    private val listenersMap = ConcurrentHashMap<String, OnProgressListener>()

    private val LISTENER = object : ProgressResponseBody.InternalProgressListener {
        override fun onProgress(url: String, bytesRead: Long, totalBytes: Long) {
            getProgressListener(url)?.let {
                val percentage = (bytesRead * 1f / totalBytes * 100f).toInt()
                val isComplete = percentage >= 100
                it.invoke(isComplete, percentage, bytesRead, totalBytes)
                if (isComplete) {
                    removeListener(url)
                }
            }
        }

    }

    // glide 下载进度的主要逻辑，需要在GlideModule注入
    fun OkHttpClient.Builder.glideProgressInterceptor(): OkHttpClient.Builder =
        addNetworkInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response.newBuilder().run {
                val body = response.body()
                if (null != body) {
                    body(ProgressResponseBody(request.url().toString(), LISTENER, body))
                }
                build()
            }
        }

    fun addListener(url: String, listener: OnProgressListener) {
        if (!TextUtils.isEmpty(url) && null != listener) {
            listenersMap[url] = listener
            listener.invoke(false, 1, 0, 0)
        }
    }

    fun removeListener(url: String) {
        if (!TextUtils.isEmpty(url)) {
            listenersMap.remove(url)
        }
    }

    fun getProgressListener(url: String?): OnProgressListener =
        if (TextUtils.isEmpty(url) || listenersMap.size == 0) null else listenersMap[url]

}