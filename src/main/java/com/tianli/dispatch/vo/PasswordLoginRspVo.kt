package com.tianli.dispatch.vo

data class PasswordLoginRspVo(
    var account: AccountInfoVo? = null,
    var device_grant_required: Boolean = false,
    var safe_mobile_required: Boolean = false,
    var realperson_required: Boolean = false,
    var reactivate_required: Boolean = false,
    var realname_operation: String = "None"
)
