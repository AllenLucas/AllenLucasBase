package com.allenlucas.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.allenlucas.base.R
import com.allenlucas.base.databinding.FragmentSecondBinding
import com.allenlucas.basiclib.base.BaseFragment

class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSecondBinding.inflate(inflater, container, false)

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        mBinding.tvSecond.onClick {
            it.navigationController().navigate(R.id.action_secondFragment_to_treeFragment)
        }
    }
}