package com.allenlucas.basiclib.img.http

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

class OkhttpUrlLoader(private val client: Call.Factory) : ModelLoader<GlideUrl, InputStream> {

    override fun buildLoadData(
        model: GlideUrl,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream> =
        ModelLoader.LoadData(model, OkHttpStreamFetcher(client, model))

    override fun handles(model: GlideUrl): Boolean = true

    class Factory @JvmOverloads constructor(private val client: Call.Factory = internalClient!!) :
        ModelLoaderFactory<GlideUrl, InputStream> {

        companion object {
            @Volatile
            private var internalClient: Call.Factory? = null
                get() {
                    if (null == field) {
                        synchronized(Factory::class.java) {
                            if (null == field) {
                                field = OkHttpClient()
                            }
                        }
                    }
                    return field
                }
        }

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> =
            OkhttpUrlLoader(client)

        override fun teardown() {
        }
    }
}