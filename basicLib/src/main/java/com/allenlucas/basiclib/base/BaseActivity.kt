package com.allenlucas.basiclib.base

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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
    private fun viewClick(view: View, click: (View) -> Unit) {
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

    fun View.onClick(click: (View) -> Unit) {
        viewClick(this, click)
    }

    fun navigationController(@IdRes id: Int): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(id)
        if (navHostFragment is NavHostFragment) {
            return navHostFragment.navController
        }
        throw IllegalArgumentException("该id无法找到正确的navHostFragment")
    }

    fun navigationFragment(@IdRes hostId: Int, @IdRes actionId: Int) {
        navigationController(hostId).navigate(actionId)
    }
}