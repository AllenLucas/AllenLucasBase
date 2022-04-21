package com.allenlucas.basiclib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.allenlucas.basiclib.lifecycle.ClickUtils

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var mBinding: VB
    private val clickUtils by lazy { ClickUtils() }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun initView(savedInstanceState: Bundle?)

    // 初始化观察者
    protected open fun initObserve() {
    }

    //初始化监听
    protected open fun initListener() {
    }

    //释放资源
    protected open fun release() {
    }

    private fun viewClick(view: View, click: (View) -> Unit) {
        clickUtils.click(view, click)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = getViewBinding(inflater, container)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickUtils.addObserve(this)
        initView(savedInstanceState)
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        release()
    }

    fun View.onClick(click: (View) -> Unit) {
        viewClick(this, click)
    }

    fun View.navigationController() = Navigation.findNavController(this)
}