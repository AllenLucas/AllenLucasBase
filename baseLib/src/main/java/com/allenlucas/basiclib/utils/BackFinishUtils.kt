package com.allenlucas.basiclib.utils

import android.app.Activity
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

/**
 * 双击back键返回工具类
 * BY AllenLucas
 * @link https://c7.gay/JqvcmoPd
 */
class BackFinishUtils(activity: Activity) {

    // 弱引用 activity
    private val mWeakReference = WeakReference(activity)

    // 使用委托方法来记录点击时间，比对两次时间的间隔
    private var backPressedTime by Delegates.observable(0L) { pre, old, new ->
        if (new - old < 2000) {
            mWeakReference.get()?.finish()
        } else {
            ToastUtil.show("再按返回键退出")
        }
    }

    /**
     * 点击返回键
     */
    fun onBackPress() {
        backPressedTime = System.currentTimeMillis()
    }
}