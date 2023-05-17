package com.tianli.auth.grasscutterauth.domain;

import java.time.LocalDateTime

data class User(
    var id: String?= null,
    var userName: String?= null,
    var nickName: String?= null,
    var password: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var sex: String? = null,
    var loginIp: String? = null,
    var loginTime: LocalDateTime? = null,
    var status: String? = null
)
