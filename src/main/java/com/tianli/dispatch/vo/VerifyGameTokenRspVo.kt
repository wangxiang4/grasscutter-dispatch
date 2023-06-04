package com.tianli.dispatch.vo

data class VerifyGameTokenRspVo(
    var guest: Boolean? = false,
    var account_type: Int? = 1,
    var account_uid: Int? = null,
    var ip_info: String? = "{\"country_code\": \"US\"}"
)