package com.allenlucas.basiclib.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.allenlucas.basiclib.lifecycle.ClickUtils

/**
 * activity基类封装
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var mBinding: VB

    // 点击帮助类，进行事件防抖
    private val clickUtils by lazy { ClickUtils() }

    /**
     * 获取viewBinding
     */
    abstract fun getViewBinding(): VB

    /**
     * view的init
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 获取数据
     */
    abstract fun initData()

    /**
     * 点击事件
     */
    protected fun viewClick(view: View, click: () -> Unit) {
        clickUtils.click(view, click)
    }

    /**
     * 监听注册
     */
    protected open fun initListener() {
    }

    /**
     * 观察者方法注册
     */
    protected open fun initObserve() {
    }

    /**
     * 资源释放的类
     */
    protected open fun release() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewBinding()
        setContentView(mBinding.root)
        initView(savedInstanceState)
        clickUtils.addObserve(this)
        initObserve()
        initListener()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        release()
    }
}