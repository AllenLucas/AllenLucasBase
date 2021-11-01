package com.allenlucas.base

import android.os.Bundle
import com.allenlucas.base.databinding.ActivityMainBinding
import com.allenlucas.basiclib.base.BaseActivity
import com.allenlucas.basiclib.utils.BackFinishUtils
import com.allenlucas.basiclib.utils.toast

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val backUtils by lazy { BackFinishUtils(this) }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.tvContent.text = "initView"
    }

    override fun initListener() {
        viewClick(mBinding.tvContent) { toast("显示${System.currentTimeMillis()}") }
    }

    override fun initData() {
    }

    override fun onBackPressed() {
        backUtils.onBackPress()
    }
}