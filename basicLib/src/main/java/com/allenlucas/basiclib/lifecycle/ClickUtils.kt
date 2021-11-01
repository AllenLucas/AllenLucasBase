package com.allenlucas.basiclib.lifecycle

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

/**
 * 点击工具类、使用flow限流进行点击事件防抖
 * https://www.codeleading.com/article/37045871556/
 */
class ClickUtils : LifecycleObserver {

    private val mainScope = MainScope()
    private val time = 1000L
    private var mOwner: LifecycleOwner? = null

    fun addObserve(owner: LifecycleOwner) {
        mOwner = owner
        mOwner?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mainScope.cancel()
        mOwner?.lifecycle?.removeObserver(this)
        mOwner = null
    }

    fun click(view: View, click: () -> Unit) {
        view.clickFlow().throttleFirst(time).onEach { click.invoke() }.launchIn(mainScope)
    }

    private fun <T> Flow<T>.throttleFirst(thresholdMillis: Long): Flow<T> = flow {
        var lastTime = 0L // 上一次发射时间
        collect { upStream ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime > thresholdMillis) {
                lastTime = currentTime
                emit(upStream)
            }
        }
    }

    private fun View.clickFlow() = callbackFlow {
        setOnClickListener { offer(Unit) }
        awaitClose { setOnClickListener(null) }
    }
}