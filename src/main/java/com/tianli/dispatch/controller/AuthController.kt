package com.tianli.dispatch.controller

import cn.hutool.core.util.ObjectUtil
import cn.hutool.core.util.StrUtil
import com.tianli.dispatch.constant.AppConstants
import com.tianli.dispatch.domain.Account
import com.tianli.dispatch.dto.AuthDto
import com.tianli.dispatch.props.DispatchProperties
import com.tianli.dispatch.service.AuthService
import com.tianli.dispatch.vo.R
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono


@RestController
@RequestMapping(AppConstants.API_DISPATCH + "/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/sendMailCaptcha")
    fun sendMailCaptcha(@RequestParam recipient: String, webSession: WebSession): Mono<R<Boolean>> {
        return if (ObjectUtil.isEmpty(webSession.attributes[recipient]))
            Mono.just(R.ok(authService.sendMailCaptcha(recipient, webSession)))
        else Mono.just(R.error(false, "验证码已发送，请过一会儿再请求"))
    }

    @PostMapping("/getAccountByToken")
    fun getAccountByToken(@RequestParam token: String): Mono<R<Account?>> {
        return Mono.just(R.ok(authService.getAccountByToken(token)))
    }

    @PostMapping("/register")
    fun register(@RequestBody account:Account, webSession: WebSession): Mono<R<Account?>> {
        // Prevent malicious attacks
        if (StrUtil.isBlank(account.username) || StrUtil.isBlank(account.password))
            return Mono.just(R.error(null, "必填属性为空请检查后在试"))
        if (StrUtil.isNotBlank(account.email)) {
            if (StrUtil.isBlank(account.code)) Mono.just(R.error(null, "验证码必填"))
            return if (webSession.attributes[account.email] as? String == account.code)
                return Mono.just(R.ok(authService.register(account)))
            else Mono.just(R.error(null, "邮箱验证码有误"))
        } else return Mono.just(R.ok(authService.register(account)))
    }

    @PostMapping("/getAccountByUsername")
    fun getAccountByUsername(@RequestParam username: String): Mono<R<Account?>> {
        return Mono.just(R.ok(authService.getAccountByUsername(username)))
    }

}
