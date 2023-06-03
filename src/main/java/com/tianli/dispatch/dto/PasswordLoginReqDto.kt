package com.tianli.dispatch.dto

data class PasswordLoginReqDto(
    var account: String? = null,
    var password: String? = null,
    var is_crypto: Boolean? = null,
)
