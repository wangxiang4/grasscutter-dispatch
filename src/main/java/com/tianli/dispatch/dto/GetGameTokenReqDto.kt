package com.tianli.dispatch.dto

data class GetGameTokenReqDto(

    var app_id: Int? = null,
    var channel_id: Int? = null,
    var data: GetGameTokenReqData? = null,
    var device: String? = null,
    var sign: String? = null
) {
    data class GetGameTokenReqData(
        var uid: Int? = null,
        var guest: Boolean? = null,
        var token: String? = null //login session key
    )
}

