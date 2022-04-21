package com.allenlucas.basiclib.utils

import android.os.Looper
import android.widget.Toast

/**
 * toast 简单封装 添加子线程显示toast
 */
object ToastUtil {

    private val context by lazy { ContextManager.instance }

    private var mToast: Toast? = null

    fun show(msg: String) {
        show(msg, Toast.LENGTH_SHORT)
    }

    fun showLong(msg: String) {
        show(msg, Toast.LENGTH_LONG)
    }

    private fun show(msg: String, duration: Int) {
        var myLooper: Looper? = null
        if (null == Looper.myLooper()) {
            Looper.prepare()
            myLooper = Looper.myLooper()
        }

        if (null == mToast) {
            mToast = Toast.makeText(context.getAppContext(), msg, duration)
        } else {
            mToast?.setText(msg)
        }
        mToast?.show()

        myLooper?.let {
            Looper.loop()
            it.quit()
        }
    }
}