package com.allenlucas.basiclib.utils

import android.content.Context
import android.widget.Toast

/**
 * 显示toast
 */
fun Context.toast(msg: String) {
    Toast.makeText(this.applicationContext, msg, Toast.LENGTH_SHORT).show()
}