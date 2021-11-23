package com.allenlucas.base.request

data class ResponseResult<T>(
    var errorCode: Int = -1,
    var errorMsg: String? = "",
    var data: T? = null
)

data class Banner(
    var id: Long = 0,
    var title: String? = "",
    var desc: String? = "",
    var url: String? = ""
)