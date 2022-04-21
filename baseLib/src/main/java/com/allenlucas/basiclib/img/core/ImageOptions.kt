package com.allenlucas.basiclib.img.core

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.allenlucas.basiclib.img.OnProgressListener
import com.bumptech.glide.load.Transformation

/**
 * 图片加载库的配置，封装原始加载配置属性，进行转换
 */
class ImageOptions {
    // 加载原始资源
    var res: Any? = null

    // 显示容器
    var imageView: ImageView? = null

    // imageView存在的上下文或者fragment/activity
    var context: Any? = null
        get() {
            return field ?: imageView
        }

    /**
     * DES: 默认图等通过代码分组管理方式
     */
    var drawableOptions = DrawableOptions.DEFAULT

    // 通过显示指定
    inline fun setDrawableOptions(options: DrawableOptions.() -> Unit) {
        drawableOptions = drawableOptions.copy().also(options)
    }

    // 记载占位图资源ID，如果placeholder是0表示没有占位图
    @DrawableRes
    var placeHolderResId: Int = 0
        get() = TODO()
        set(value) {
            setDrawableOptions { placeHolderResId = value }
            field = value
        }

    // 加载占位图资源Drawable对象
    var placeHolderDrawable: Drawable?
        get() = TODO()
        set(value) {
            setDrawableOptions { placeHolderDrawable = value }
        }

    // 错误占位图资源ID
    @DrawableRes
    var errorResId = 0
        get() = TODO()
        set(value) {
            setDrawableOptions { errorResId = value }
            field = value
        }

    // 错误占位图资源对象
    var errorDrawable: Drawable?
        get() = TODO()
        set(value) {
            setDrawableOptions { errorDrawable = value }
        }

    //fallback
    @DrawableRes
    var fallbackResId = 0
        get() = TODO()
        set(value) {
            setDrawableOptions { fallbackResId = value }
            field = value
        }

    var fallbackDrawable: Drawable?
        get() = TODO()
        set(value) {
            setDrawableOptions { fallbackDrawable = value }
        }

    var isCrossFade = true  // 是否渐隐加载
    var skipMemoryCache: Boolean = false  // 是否跳过内存缓存
    var isAnim: Boolean = true  // 是否使用动画
    var diskCacheStrategy = DiskCache.AUTOMATIC  //磁盘缓存
    var priority = LoadPriority.NORMAL  // 加载优先级
    var thumbnail: Float = 0f  //加载缩略图
    var thumbnailUrl: Any? = null  // 缩略图链接
    var size: OverrideSize? = null  // 图片的尺寸

    /**
     * 特效处理：圆形图片
     * Glide要将isCrossFade设置为false，不然会影响显示效果
     */
    var isCircle: Boolean = false
    var isGray: Boolean = false
    var centerCrop: Boolean = false
    var isFitCenter: Boolean = false

    var format: Bitmap.Config? = null //图片配置
    var borderWidth: Int = 0  //圆形是否带边框

    @ColorInt
    var borderColor: Int = Color.TRANSPARENT  // 圆形边框的颜色

    /**
     * 模糊特效
     * Glide要将isCrossFade设置为false，不然会影响展示效果
     */
    var isBlur: Boolean = false
    var blurRadius: Int = 25  //高斯模糊半径
    var blurSampling: Int = 4  //缩放系数，加速模糊速度

    /**
     * 是否圆角
     * Glide要将isCrossFade设置为false，不然会影响展示效果
     */
    var isRoundedCorners: Boolean = false
    var roundRadius: Int = 0  // 圆角弧度
    var cornerType: CornerType = CornerType.ALL  //圆角边向

    enum class CornerType {
        ALL, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, TOP, BOTTOM, LEFT, RIGHT, OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT, DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
    }

    var transformation: Array<out Transformation<Bitmap>>? = null  //转换器

    // 网络进度监听器
    var onProgressListener: OnProgressListener = null
        private set

    fun progressListener(listener: OnProgressListener) {
        this.onProgressListener = listener
    }

    // 加载监听
    var requestListener: OnImageListener? = null
        private set

    fun requestListener(listener: OnImageListener.() -> Unit) {
        requestListener = OnImageListener().also(listener)
    }

    data class DrawableOptions(
        // 加载占位图资源ID，如果placeholder是0表示没有占位图
        @DrawableRes var placeHolderResId: Int = 0, var placeHolderDrawable: Drawable? = null,
        //错误占位图资源ID
        @DrawableRes var errorResId: Int = 0, var errorDrawable: Drawable? = null,
        @DrawableRes
        var fallbackResId: Int = 0, var fallbackDrawable: Drawable? = null
    ) {
        companion object {
            var DEFAULT = DrawableOptions()

            inline fun setDefault(options: DrawableOptions.() -> Unit) {
                DEFAULT = DEFAULT.also(options)
            }
        }
    }

    /**
     * 指定加载图片的大小
     */
    class OverrideSize(val width: Int, val height: Int)

    /**
     * 硬盘缓存策略
     */
    enum class DiskCache(val strategy: Int) {
        //没有缓存
        NONE(1),

        //根据原始图片数据和资源编码策略来自动选择磁盘缓存策略，需要写入权限
        AUTOMATIC(2),

        //在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源
        RESOURCE(3),

        //在资源解码前就将原始数据写入磁盘缓存，需要写入权限
        DATA(4),

        // 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据，需要写入权限
        ALL(5)
    }

    /**
     * 加载优先级策略
     * 指定了图片加载的优先级后，加载时会按照图片的优先级进行顺序加载
     * IMMEDIATE优先级时会直接加载，不需要等待
     */
    enum class LoadPriority(val priority: Int) {
        // 低优先级
        LOW(1),

        //普通优先级
        NORMAL(2),

        //高优先级
        HIGH(3),

        //立即加载，无需等待
        IMMEDIATE(4)
    }
}