package com.allenlucas.base.repository

import androidx.lifecycle.MutableLiveData
import com.allenlucas.base.base.AppBaseRepository
import com.allenlucas.base.request.Banner

class BannerRepository : AppBaseRepository() {

    suspend fun getBanner(liveData: MutableLiveData<List<Banner>>) {
        request {
            val res = api.getBanner()
            if (null != res.data) {
                liveData.postValue(res.data)
            }
        }
    }

}