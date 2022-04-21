package com.allenlucas.basiclib.img.transformation

import android.graphics.*
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * 黑白特效
 */
class GrayscaleTransformation : BitmapTransformation() {

    companion object {
        private const val VERSION = 1
        private const val ID = "glide.transformations.GrayscaleTransformation.$VERSION"
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID.toByteArray(Key.CHARSET))
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        val config = if (null != toTransform.config) toTransform.config else Bitmap.Config.ARGB_8888
        val bitmap = pool[width, height, config]
        val canvas = Canvas(bitmap)
        val saturation = ColorMatrix()
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(saturation)
        canvas.drawBitmap(toTransform, 0f, 0f, paint)
        return bitmap
    }

    override fun toString(): String {
        return "GrayscaleTransformation()"
    }

    override fun hashCode(): Int = ID.hashCode()

    override fun equals(other: Any?): Boolean = other is GrayscaleTransformation

}