package com.allenlucas.base.request

import com.allenlucas.basiclib.request.BaseApi
import retrofit2.http.GET

interface IApi : BaseApi {

    @GET("banner/json")
    suspend fun getBanner(): ResponseResult<List<Banner>>
}