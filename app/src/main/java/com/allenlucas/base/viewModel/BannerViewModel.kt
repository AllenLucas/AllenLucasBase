package com.allenlucas.base.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allenlucas.base.repository.BannerRepository
import com.allenlucas.base.request.Banner
import kotlinx.coroutines.launch

class BannerViewModel : ViewModel() {

    val mBannerLiveData = MutableLiveData<List<Banner>>()
    private val bannerRepository = BannerRepository()

    fun getBanner() {
        viewModelScope.launch {
            bannerRepository.getBanner(mBannerLiveData)
        }
    }

}