package com.tianli.dispatch.controller

import com.tianli.dispatch.constant.AppConstants
import com.tianli.dispatch.domain.User
import com.tianli.dispatch.service.AuthService
import com.tianli.dispatch.vo.R
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono


@RestController
@RequestMapping(AppConstants.API_DISPATCH + "/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/sendMailCaptcha")
    fun sendMailCaptcha(@RequestParam recipient: String, webSession: WebSession): Mono<R<Boolean>> {
        return Mono.just(authService.sendMailCaptcha(recipient, webSession))
    }

    @PostMapping("/register")
    fun register(@RequestBody user:User) {

    }

}
