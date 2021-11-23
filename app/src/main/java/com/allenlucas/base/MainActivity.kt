package com.allenlucas.base

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.allenlucas.base.base.MainFragment
import com.allenlucas.base.databinding.ActivityMainBinding
import com.allenlucas.base.viewModel.BannerViewModel
import com.allenlucas.basiclib.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val viewModel by viewModels<BannerViewModel>()

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        val mainFragment = MainFragment()
//        mBinding.tvContent.text = "initView"
    }

    override fun initObserve() {
        super.initObserve()
        viewModel.mBannerLiveData.observe(this){
            it.forEach {
                Log.e("lal","result:${it.title}")
            }
        }
    }

    override fun initListener() {
        mBinding.btnStart.onClick {
            viewModel.getBanner()
        }
//        mBinding.tvContent.onClick { toast("显示${System.currentTimeMillis()}") }
    }

    override fun initData() {
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

}