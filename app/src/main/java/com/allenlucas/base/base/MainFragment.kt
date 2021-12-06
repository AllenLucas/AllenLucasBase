package com.allenlucas.base.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import com.allenlucas.base.R
import com.allenlucas.base.databinding.FragmentMainBinding
import com.allenlucas.basiclib.base.BaseFragment
import com.allenlucas.basiclib.img.GlideImageLoader
import com.allenlucas.basiclib.img.core.ImageOptions
import com.allenlucas.basiclib.utils.BackFinishUtils

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val gifs = "https://t7.baidu.com/it/u=3713375227,571533122&fm=193&f=GIF"

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
        GlideImageLoader.instance.loadImage(ImageOptions().apply {
            imageView = mBinding.ivImage
            res = gifs
            progressListener { isComplete, percentage, bytesRead, totalBytes ->
                Log.e("lal","---> isComplete:$isComplete percentage: $percentage bytesRead:$bytesRead totalBytes:$totalBytes")
            }
        })
    }
}