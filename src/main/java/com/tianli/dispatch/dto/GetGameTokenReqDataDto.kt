package com.tianli.dispatch.dto

data class GetGameTokenReqDataDto(
    var uid: Int? = null,
    var guest: Boolean? = null,
    var token: String? = null //login session key
)
