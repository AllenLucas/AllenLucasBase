package com.allenlucas.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.allenlucas.base.databinding.FragmentMainBinding
import com.allenlucas.basiclib.base.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun initView(savedInstanceState: Bundle?) {
    }
}