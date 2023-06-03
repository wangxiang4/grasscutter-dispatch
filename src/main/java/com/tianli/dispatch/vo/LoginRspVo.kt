package com.tianli.dispatch.vo

data class LoginRspVo(
    var account: AccountInfoVo? = null,
    var device_grant_required: Boolean? = null,
    var safe_mobile_required: Boolean? = null,
    var realperson_required: Boolean? = null,
    var reactivate_required: Boolean? = null,
    var realname_operation: String = "None"
)
