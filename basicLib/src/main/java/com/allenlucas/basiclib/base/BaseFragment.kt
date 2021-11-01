package com.allenlucas.basiclib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.allenlucas.basiclib.lifecycle.ClickUtils

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var mBinding: VB
    private val clickUtils by lazy { ClickUtils() }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun initView(savedInstanceState: Bundle?)

    protected fun initObserve() {
    }

    protected fun initListener() {
    }

    protected fun release() {
    }

    protected fun viewClick(view: View, click: () -> Unit) {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        release()
    }
}