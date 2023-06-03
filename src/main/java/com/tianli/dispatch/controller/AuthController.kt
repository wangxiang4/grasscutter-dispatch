package com.tianli.dispatch.controller

import cn.hutool.core.util.ObjectUtil
import cn.hutool.core.util.StrUtil
import com.tianli.dispatch.constant.AppConstants
import com.tianli.dispatch.domain.Account
import com.tianli.dispatch.service.AuthService
import com.tianli.dispatch.util.SecurityUtil
import com.tianli.dispatch.vo.R
import com.tianli.dispatch.vo.ResultVo
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
            if (StrUtil.isBlank(account.code)) return Mono.just(R.error(null, "验证码必填"))
            return if (webSession.attributes[account.email] as? String == account.code)
                return Mono.just(R.ok(authService.register(account)))
            else Mono.just(R.error(null, "邮箱验证码有误"))
        } else return Mono.just(R.ok(authService.register(account)))
    }

    @PostMapping("/getAccountByUsername")
    fun getAccountByUsername(@RequestParam username: String): Mono<R<Account?>> {
        return Mono.just(R.ok(authService.getAccountByUsername(username)))
    }

    @PostMapping("/getForgotpadToken")
    fun getForgotpadToken(@RequestBody account:Account, webSession: WebSession): Mono<R<ResultVo<Any>?>> {
        // Prevent malicious attacks
        if (StrUtil.isBlank(account.email) || StrUtil.isBlank(account.code))
            return Mono.just(R.error(null, "必填属性为空请检查后在试"))
        return if (webSession.attributes[account.email] as? String == account.code) {
            val token = SecurityUtil.bytesToHex(SecurityUtil.createSessionKey(32))
            webSession.attributes["${account.email}-${AppConstants.FORGOTPAD_TOKEN_SUFFIX}"] = token
            account.forgotpadToken = token
            val result = ResultVo<Any>()
            result.result = account
            val accountList = authService.selectMailBindAccount(account.email!!)
            if (accountList.isEmpty()) Mono.just(R.error(null, "当前邮箱没有绑定的账户"))
            result.extend = accountList
            return Mono.just(R.ok(result))
        } else Mono.just(R.error(null, "邮箱验证码有误"))
    }

    @PostMapping("/resetPassword")
    fun resetPassword(@RequestBody account:Account, webSession: WebSession): Mono<R<Boolean>> {
        if (StrUtil.isBlank(account.password) || StrUtil.isBlank(account.username))
            return Mono.just(R.error(false, "必填属性为空请检查后在试"))
        if (StrUtil.isBlank(account.forgotpadToken)) return Mono.just(R.error(false, "邮箱验证已过期请刷新页面重新验证"))
        return if (webSession.attributes["${account.email}-${AppConstants.FORGOTPAD_TOKEN_SUFFIX}"] as? String == account.forgotpadToken) {
            val result = authService.resetPassword(account, webSession)
            if (!result) return Mono.just(R.error(false, "当前邮箱没有绑定的账户"))
            return Mono.just(R.ok(true))
        } else Mono.just(R.error(false, "Token无效请刷新页面重新验证"))
    }

    @PostMapping("/selectMailBindAccount")
    fun selectMailBindAccount(@RequestParam email:String): Mono<R<List<Account>>> {
        return Mono.just(R.ok(authService.selectMailBindAccount(email)))
    }

}
