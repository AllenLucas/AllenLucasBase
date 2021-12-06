package com.allenlucas.basiclib.img.transformation

import android.content.res.Resources
import android.graphics.*
import androidx.annotation.ColorInt
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * 带边框圆形特效
 */
class CircleWithBorderTransformation(borderWidth: Int, @ColorInt borderColor: Int) :
    BitmapTransformation() {

    private val mBorderWidth: Float = Resources.getSystem().displayMetrics.density * borderWidth
    private val id = javaClass.name

    private val mBorderPaint: Paint = Paint().apply {
        isDither = true
        isAntiAlias = true
        color = borderColor
        style = Paint.Style.STROKE
        strokeWidth = mBorderWidth
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update((id + mBorderWidth * 10).toByteArray(Key.CHARSET))
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap = circleCrop(pool, toTransform)!!

    private fun circleCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        source ?: return null
        val size = (source.width.coerceAtMost(source.height) - mBorderWidth / 2).toInt()
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val squared = Bitmap.createBitmap(source, x, y, size, size)
        var result: Bitmap? = pool[size, size, Bitmap.Config.ARGB_8888]
        if (null == result) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        }
        // 创建画笔，画布，手动描绘边框
        val canvas = result?.let { Canvas(it) }
        val paint = Paint()
        paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val r = size / 2f
        canvas?.drawCircle(r, r, r, paint)
        val borderRadius = r - mBorderWidth / 2
        canvas?.drawCircle(r, r, borderRadius, mBorderPaint)
        return result
    }

}