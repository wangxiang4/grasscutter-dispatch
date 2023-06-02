package com.tianli.dispatch.controller;

import com.tianli.dispatch.constant.AppConstants;
import com.tianli.dispatch.service.SdkService
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/" + AppConstants.SDK_NAME)
class SdkController(private val sdkService: SdkService) {

    //password login
    data class PasswordLoginReq(val account: String, val password: String, val is_crypto: Boolean)
    @PostMapping("/mdk/shield/api/login")
    fun passwordLogin(@RequestBody passwordLoginReq: PasswordLoginReq):Mono<String>{
        var account = sdkService.passwordLogin(passwordLoginReq.account, passwordLoginReq.password, passwordLoginReq.is_crypto)

    }
}
