package com.allenlucas.basiclib.img.transformation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import com.allenlucas.basiclib.img.utils.BlurUtils
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Util
import java.security.MessageDigest

/**
 * 高斯模糊特效
 */
class BlurTransformation @JvmOverloads constructor(
    private val context: Context,
    radius: Int = MAX_RADIUS,
    sampling: Int = DEFAULT_SAMPLING
) : BitmapTransformation() {

    companion object {
        private const val MAX_RADIUS = 25
        private const val DEFAULT_SAMPLING = 1
    }

    private val id = javaClass.name
    private val radius = if (radius > MAX_RADIUS) MAX_RADIUS else radius  //模糊半径0~25
    private val sampling = if (sampling > MAX_RADIUS) MAX_RADIUS else sampling //取样0~25

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update((id + radius * 10 + sampling).toByteArray(Key.CHARSET))
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        val scaledWidth = width / sampling
        val scaledHeight = height / sampling
        var bitmap = pool[scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888]
        val canvas = Canvas(bitmap)
        canvas.scale(1 / sampling.toFloat(), 1 / sampling.toFloat())
        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(toTransform, 0f, 0f, paint)
        bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            BlurUtils.rsBlur(context, bitmap, radius)
        } else {
            BlurUtils.blur(bitmap, radius)!!
        }
        return bitmap
    }

    override fun hashCode(): Int =
        Util.hashCode(id.hashCode(), Util.hashCode(radius, Util.hashCode(sampling)))

    override fun equals(other: Any?): Boolean {
        if (other is BlurTransformation){
            return radius == other.radius && sampling == other.sampling
        }
        return false
    }
}