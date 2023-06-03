package com.tianli.dispatch.vo

data class AccountInfoVo(
    var uid: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var mobile: String? = null,
    var is_email_verify: String = "0",
    var realname: String? = null,
    var identity_card: String? = null,
    var token: String? = null, //session key
    var safe_mobile: String? = null,
    var facebook_name: String? = null,
    var google_name: String? = null,
    var twitter_name: String? = null,
    var game_center_name: String? = null,
    var apple_name: String? = null,
    var sony_name: String? = null,
    var tap_name: String? = null,
    var country: String = "US",
    var reactivate_ticket: String? = null,
    var area_code: String = "**",
    var device_grant_ticket: String? = null,
    var steam_name: String? = null,
)
