package com.allenlucas.basiclib.img

import android.content.Context
import com.allenlucas.basiclib.img.progress.ProgressManager.glideProgressInterceptor
import com.allenlucas.basiclib.img.http.OkhttpUrlLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule(glideName = "IGlideModule")
class GlideModule : AppGlideModule() {

    companion object {
        // 把glide配置方法进行暴露接口
        var options: IGlideOptions? = null
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        options?.applyOptions(context, builder)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // 下载进度实现
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkhttpUrlLoader.Factory(OkHttpClient.Builder().glideProgressInterceptor().build())
        )
        options?.registerComponents(context, glide, registry)
    }

    override fun isManifestParsingEnabled(): Boolean = options?.isManifestParsingEnabled() ?: false

}

/**
 * 把Glide配置方法进行保留
 */
interface IGlideOptions {
    fun isManifestParsingEnabled(): Boolean = false
    fun applyOptions(context: Context, builder: GlideBuilder)

    // glide 下载进度的主要逻辑 需要在OkHttpClient.Builder().glideProgressInterceptor()
    fun registerComponents(context: Context, glide: Glide, registry: Registry)
}