package com.tianli.dispatch.domain

import java.time.LocalDateTime

data class User(
    var id: String?= null,
    var createPlayerId: Int?= null,
    var userName: String?= null,
    var password: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var token: String? = null,
    var sessionKey: String? = null,
    var locale: String? = null,
    var banStartTime: Int? = null,
    var banEndTime: Int? =null,
    var ban: String? = null,
    var createTime: LocalDateTime? = null,
    var updateTime: LocalDateTime? = null,
    var remarks: String? = null,
    var delFlag: String? = null,
)
