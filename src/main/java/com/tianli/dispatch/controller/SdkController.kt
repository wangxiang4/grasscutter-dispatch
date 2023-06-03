package com.tianli.dispatch.controller;

import com.tianli.dispatch.constant.AppConstants;
import com.tianli.dispatch.service.SdkService
import com.tianli.dispatch.vo.SdkR
import com.tianli.dispatch.vo.SdkRspCode
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/" + AppConstants.SDK_NAME)
class SdkController(private var sdkService: SdkService) {
    data class AccountInfo(
        var uid: Int,
        var name: String,
        var email: String,
        var mobile: String? = null,
        var is_email_verify: String = "0",
        var realname: String? = null,
        var identity_card: String? = null,
        var token: String, //session key
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
        var steam_name: String? = null
    )

    //password login
    data class PasswordLoginReq(
        var account: String,
        var password: String,
        var is_crypto: Boolean
    )

    data class PasswordLoginRsp(
        var account: AccountInfo,
        var device_grant_required: Boolean = false,
        var safe_mobile_required: Boolean = false,
        var realperson_required: Boolean = false,
        var reactivate_required: Boolean = false,
        var realname_operation: String = "None"
    )

    @PostMapping("/mdk/shield/api/login")
    fun passwordLogin(@RequestBody passwordLoginReq: PasswordLoginReq): Mono<SdkR<PasswordLoginRsp?>> {
        //TODO: ban blacklist ip, error code=-206
        //check blank field
        if (passwordLoginReq.account.isBlank() || passwordLoginReq.password.isBlank()) {
            return Mono.just(SdkR.error(SdkRspCode.USERNAME_EMPTY))
        }
        val account =
            sdkService.passwordLogin(passwordLoginReq.account, passwordLoginReq.password, passwordLoginReq.is_crypto)
                ?: return Mono.just(SdkR.error(SdkRspCode.ACCOUNT_OR_PASSWORD_ERROR))
        val accountInfo = AccountInfo(
            uid = account.id!!,
            name = account.email!!,
            email = account.email!!,
            token = account.sessionKey!!
        )
        val rsp = PasswordLoginRsp(
            account = accountInfo
        )
        return Mono.just(SdkR.ok(rsp))
    }

}
