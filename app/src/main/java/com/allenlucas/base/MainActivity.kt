package com.allenlucas.base

import android.os.Bundle
import com.allenlucas.base.base.MainFragment
import com.allenlucas.base.databinding.ActivityMainBinding
import com.allenlucas.basiclib.base.BaseActivity
import com.allenlucas.basiclib.utils.BackFinishUtils

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val backUtils by lazy { BackFinishUtils(this) }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        val mainFragment = MainFragment()
//        mBinding.tvContent.text = "initView"
    }

    override fun initListener() {
        mBinding.btnStart.onClick {
            navigationFragment(R.id.nav_host_fragment, R.id.action_mainFragment_to_secondFragment)
        }
//        mBinding.tvContent.onClick { toast("显示${System.currentTimeMillis()}") }
    }

    override fun initData() {
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
//        val nav = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//        if (nav is MainFragment) {
//            backUtils.onBackPress()
//        } else {
//            super.onBackPressed()
//        }
    }

}