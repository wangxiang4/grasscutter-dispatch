package com.tianli.dispatch.dto

data class GetGameTokenReqDto(

    var app_id: Int? = null,
    var channel_id: Int? = null,
    var data: String? = null,
    var device: String? = null,
    var sign: String? = null

)

