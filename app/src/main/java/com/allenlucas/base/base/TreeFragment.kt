package com.allenlucas.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.allenlucas.base.databinding.FragmentTreeBinding
import com.allenlucas.basiclib.base.BaseFragment

class TreeFragment : BaseFragment<FragmentTreeBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTreeBinding.inflate(inflater, container, false)

    override fun initView(savedInstanceState: Bundle?) {
    }
}