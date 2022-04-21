package com.allenlucas.basiclib.img.progress

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * 计算进度
 */
class ProgressResponseBody internal constructor(
    private val url: String,
    private val internalProgressListener: InternalProgressListener?,
    private val responseBody: ResponseBody
) : ResponseBody() {

    companion object {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
    }

    private var bufferedSource: BufferedSource? = null

    internal interface InternalProgressListener {
        fun onProgress(url: String, bytesRead: Long, totalBytes: Long)
    }

    override fun contentType(): MediaType? = responseBody.contentType()

    override fun contentLength(): Long = responseBody.contentLength()

    override fun source(): BufferedSource {
        if (null == bufferedSource) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource!!
    }

    private fun source(source: Source): Source = object : ForwardingSource(source) {
        var totalBytesRead: Long = 0
        var lastTotalBytesRead: Long = 0

        @Throws(IOException::class)
        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            totalBytesRead += if (bytesRead == -1L) 0 else bytesRead
            if (null != internalProgressListener && lastTotalBytesRead != totalBytesRead) {
                lastTotalBytesRead = totalBytesRead
                mainThreadHandler.post {
                    internalProgressListener.onProgress(
                        url,
                        totalBytesRead,
                        contentLength()
                    )
                }
            }
            return bytesRead
        }
    }
}