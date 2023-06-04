package com.tianli.dispatch.vo

data class GetGameTokenRspVo(
    var combo_id: String? = "0", //ignored
    var open_id: Int? = null, //uid
    var account_type: Int? = 1,
    var combo_token: String? = null, //game token
    var data: String? = "{\"guest\":false}",
    var fatigue_remind: Any? = null,
    var heartbeat: Boolean? = false
)


