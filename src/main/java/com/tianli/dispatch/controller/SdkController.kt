package com.tianli.dispatch.controller;

import cn.hutool.core.util.StrUtil
import com.tianli.dispatch.api.SdkR
import com.tianli.dispatch.constant.AppConstants;
import com.tianli.dispatch.dto.PasswordLoginReqDto
import com.tianli.dispatch.enums.SdkRspCodeEnum
import com.tianli.dispatch.service.SdkService
import com.tianli.dispatch.vo.AccountInfoVo
import com.tianli.dispatch.vo.PasswordLoginRspVo
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *<p>
 * sdk dispatch auth controller
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/6/3
 */
@RestController
@RequestMapping( AppConstants.API_SDK_NAME)
class SdkController(private val sdkService: SdkService) {

    @PostMapping("/mdk/shield/api/login")
    fun passwordLogin(@RequestBody passwordLoginReq: PasswordLoginReqDto): Mono<SdkR<PasswordLoginRspVo?>> {
        //TODO: ban blacklist ip, error code=-206
        //check blank field
        if (StrUtil.isBlank(passwordLoginReq.account) ||
            StrUtil.isBlank(passwordLoginReq.password)) return Mono.just(SdkR.error(SdkRspCodeEnum.USERNAME_EMPTY))
        val account = passwordLoginReq.account?.let {
            passwordLoginReq.password?.let { it1 ->
            passwordLoginReq.is_crypto?.let { it2 -> sdkService.passwordLogin(it, it1, it2) }}}
            ?: return Mono.just(SdkR.error(SdkRspCodeEnum.ACCOUNT_OR_PASSWORD_ERROR))
        val accountInfo = AccountInfoVo(
            uid = account.id,
            name = account.email,
            email = account.email,
            token = account.sessionKey
        )
        TODO("Not yet implemented")
        val rsp = PasswordLoginRspVo(account = accountInfo)
        return Mono.just(SdkR.ok(rsp))
    }

}
