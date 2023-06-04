package com.tianli.dispatch.dto

data class VerifyGameTokenReqDto(
    var app_id: Int? = null,
    var channel_id: Int? = null,
    var open_id: Int? = null,
    var combo_token: String? = null,
    var sign: String? = null,
    var region: String? = null
)