package com.allenlucas.basiclib.img

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.allenlucas.basiclib.img.core.GlideImageViewTarget
import com.allenlucas.basiclib.img.core.ImageOptions
import com.allenlucas.basiclib.img.progress.ProgressManager
import com.allenlucas.basiclib.img.transformation.*
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.util.Preconditions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

/**
 * Glide 封装
 * 参考  https://github.com/forJrking/ImageExt
 */
class GlideImageLoader private constructor() {

    companion object {
        val instance by lazy { GlideImageLoader() }
    }

    fun loadImage(options: ImageOptions) {
        Preconditions.checkNotNull(options, "ImageConfigImpl is required")
        val context = options.context
        Preconditions.checkNotNull(context, "Context is required")
        Preconditions.checkNotNull(options.imageView, "ImageView is required")

        val requestsWith = glideRequests(context)

        val glideRequest = when (options.res) {
            is String -> requestsWith.load(options.res)
            is Bitmap -> requestsWith.load(options.res)
            is Drawable -> requestsWith.load(options.res)
            is Uri -> requestsWith.load(options.res)
            is URL -> requestsWith.load(options.res)
            is File -> requestsWith.load(options.res)
            is Int -> requestsWith.load(options.res)
            is ByteArray -> requestsWith.load(options.res)
            else -> requestsWith.load(options.res)
        }

        glideRequest.apply {
            // 缩略图大小
            if (options.thumbnail > 0f) {
                thumbnail(options.thumbnail)
            }

            options.thumbnailUrl?.let {
                thumbnail(glideRequests(context).load(it))
            }

            //缓存配置
            val diskCacheStrategy = when (options.diskCacheStrategy) {
                ImageOptions.DiskCache.ALL -> DiskCacheStrategy.ALL
                ImageOptions.DiskCache.NONE -> DiskCacheStrategy.NONE
                ImageOptions.DiskCache.RESOURCE -> DiskCacheStrategy.RESOURCE
                ImageOptions.DiskCache.DATA -> DiskCacheStrategy.DATA
                ImageOptions.DiskCache.AUTOMATIC -> DiskCacheStrategy.AUTOMATIC
            }
            diskCacheStrategy(diskCacheStrategy)

            //优先级
            val priority = when (options.priority) {
                ImageOptions.LoadPriority.LOW -> Priority.LOW
                ImageOptions.LoadPriority.NORMAL -> Priority.NORMAL
                ImageOptions.LoadPriority.HIGH -> Priority.HIGH
                ImageOptions.LoadPriority.IMMEDIATE -> Priority.IMMEDIATE
            }
            priority(priority)
            skipMemoryCache(options.skipMemoryCache)

            val drawableOptions = options.drawableOptions

            // 设置占位符
            if (null != drawableOptions.placeHolderDrawable) {
                placeholder(drawableOptions.placeHolderDrawable)
            } else if (0 != drawableOptions.placeHolderResId) {
                placeholder(drawableOptions.placeHolderResId)
            }

            // 设置错误图片
            if (null != drawableOptions.errorDrawable) {
                error(drawableOptions.errorDrawable)
            } else if (0 != drawableOptions.errorResId) {
                error(drawableOptions.errorResId)
            }

            // 设置url为空图片
            if (null != drawableOptions.fallbackDrawable) {
                fallback(drawableOptions.fallbackDrawable)
            } else if (0 != drawableOptions.fallbackResId) {
                fallback(drawableOptions.fallbackResId)
            }

            val size = options.size
            size?.let { override(it.width, it.height) }

            val format = when (options.format) {
                Bitmap.Config.ARGB_8888 -> DecodeFormat.PREFER_ARGB_8888
                Bitmap.Config.RGB_565 -> DecodeFormat.PREFER_RGB_565
                else -> DecodeFormat.DEFAULT
            }
            format(format)

            // region ========== 特效===========
            if (!options.isAnim) {
                dontAnimate()
            }
            if (options.isCrossFade) {
                val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                transition(DrawableTransitionOptions.withCrossFade(factory))
            }
            if (options.centerCrop) {
                centerCrop()
            }
            if (options.isFitCenter) {
                fitCenter()
            }

            if (options.isCircle || options.borderWidth > 0) {
                transform(CircleWithBorderTransformation(options.borderWidth, options.borderColor))
            } else {
                transform(BorderTransformation(options.borderWidth, options.borderColor))
            }

            // 是否设置圆角特效
            if (options.isRoundedCorners) {
                var transformation: BitmapTransformation? = null
                val scaleType = options.imageView?.scaleType
                if (ImageView.ScaleType.FIT_CENTER == scaleType || ImageView.ScaleType.CENTER_INSIDE == scaleType ||
                    ImageView.ScaleType.CENTER == scaleType || ImageView.ScaleType.CENTER_CROP == scaleType
                ) {
                    transformation = CenterCrop()
                }
                if (null == transformation) {
                    transform(
                        RoundedCornersTransformation(options.roundRadius, 0, options.cornerType)
                    )
                } else {
                    transform(
                        transformation,
                        RoundedCornersTransformation(options.roundRadius, 0, options.cornerType)
                    )
                }
            }

            if (options.isBlur) {
                transform(
                    BlurTransformation(
                        options.imageView!!.context, options.blurRadius, options.blurSampling
                    )
                )
            }

            if (options.isGray) {
                transform(GrayscaleTransformation())
            }

            options.transformation?.let { transform(*options.transformation!!) }

            options.requestListener?.let {
                addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        options.requestListener?.onFailAction?.invoke(e.toString())
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        options.requestListener?.onSuccessAction?.invoke(resource)
                        return false
                    }
                })
            }

            into(GlideImageViewTarget(options.imageView, options.res))
        }

        options.onProgressListener?.let {
            ProgressManager.addListener(options.res.toString(), options.onProgressListener)
        }
    }

    private fun glideRequests(context: Any?): GlideRequests = when (context) {
        is Context -> IGlideModule.with(context)
        is Activity -> IGlideModule.with(context)
        is FragmentActivity -> IGlideModule.with(context)
        is Fragment -> IGlideModule.with(context)
        is android.app.Fragment -> IGlideModule.with(context)
        is View -> IGlideModule.with(context)
        else -> throw  NullPointerException("not support")
    }

    /**
     * 清除本地缓存
     */
    suspend fun clearDiskCache(context: Context) = withContext(Dispatchers.IO) {
        Glide.get(context).clearDiskCache()
    }

    /**
     * 清除内存缓存
     */
    fun clearMemory(context: Context) {
        Glide.get(context).clearMemory()
    }

    /**
     * 取消图片加载
     */
    fun clearImage(context: Context, imageView: ImageView?) {
        Glide.get(context).requestManagerRetriever[context].clear(imageView ?: return)
    }

    /**
     * 预加载
     */
    fun preLoadImage(context: Any?, url: String) {
        glideRequests(context).load(url).preload()
    }

    /**
     * 重新加载
     */
    fun resumeRequests(context: Any?) {
        glideRequests(context).resumeRequests()
    }

    /**
     * 暂停加载
     */
    fun pauseRequests(context: Any?) {
        glideRequests(context).pauseRequests()
    }

    /**
     * 下载
     */
    suspend fun downloadImage(context: Context, imgUrl: String?): File? =
        withContext(Dispatchers.IO) {
            var extension = MimeTypeMap.getFileExtensionFromUrl(imgUrl)
            if (extension.isNullOrEmpty()) extension = "png"
            val file = Glide.with(context).download(imgUrl).submit().get()
            val appDir = context.getExternalFilesDir("img")
            if (!appDir!!.exists()) {
                appDir.mkdirs()
            }
            //保存文件名
            val fileName = "img_${System.nanoTime()}.$extension"
            val targetFile = File(appDir, fileName)
            file.copyTo(targetFile)
            val mimeTypes = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            MediaScannerConnection.scanFile(
                context,
                arrayOf(targetFile.absolutePath),
                arrayOf(mimeTypes),
                null
            )
            targetFile
        }
}