package com.tianli.dispatch.domain

import java.time.LocalDateTime

data class Account(
    var id: Int?= null, //uid
    var username : String? = null, //用户名
    var password: String? = null, //密码
    var email: String? = null, //邮箱，注册时可选填写，若为空则无法找回
    var phone: String? = null, //尚未使用
    var gameToken: String? = null, //game server token
    var sessionKey: String? = null, //自动登录会话key
    var locale: String? = null, //尚未使用
    var banStartTime: Int? = null,
    var banEndTime: Int? =null,
    var ban: String? = null,
    var createTime: LocalDateTime? = null,
    var updateTime: LocalDateTime? = null,
    var remarks: String? = null,
    var code: String? = null,
    var delFlag: String? = null,
    var test: Boolean? = null
)
