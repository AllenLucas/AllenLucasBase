package com.allenlucas.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import com.allenlucas.base.R
import com.allenlucas.base.databinding.FragmentMainBinding
import com.allenlucas.base.viewModel.BannerViewModel
import com.allenlucas.basiclib.base.BaseFragment
import com.allenlucas.basiclib.utils.BackFinishUtils

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val backUtils by lazy { BackFinishUtils(requireActivity()) }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun initListener() {
        mBinding.tvMainFragment.onClick {
            it.navigationController().navigate(R.id.action_mainFragment_to_secondFragment)
        }
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            backUtils.onBackPress()
        }
        callback.isEnabled = true
    }

    override fun initView(savedInstanceState: Bundle?) {
    }
}