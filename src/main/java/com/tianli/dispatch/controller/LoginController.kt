package com.tianli.dispatch.controller

import com.tianli.dispatch.service.LoginService
import com.tianli.dispatch.vo.R
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/auth/login")
class LoginController(private val loginService: LoginService) {

    @PostMapping("/sendMailCaptcha")
    fun sendMailCaptcha(@RequestParam recipient: String, webSession: WebSession): Mono<R<Boolean>> {
        return Mono.just(loginService.sendMailCaptcha(recipient, webSession))
    }

}
