package com.tianli.dispatch.controller;

import cn.hutool.core.util.StrUtil
import com.tianli.dispatch.api.SdkR
import com.tianli.dispatch.constant.AppConstants;
import com.tianli.dispatch.domain.Account
import com.tianli.dispatch.dto.GetGameTokenReqDto
import com.tianli.dispatch.dto.PasswordLoginReqDto
import com.tianli.dispatch.dto.SessionKeyLoginReqDto
import com.tianli.dispatch.enums.SdkRspCodeEnum
import com.tianli.dispatch.service.SdkService
import com.tianli.dispatch.vo.AccountInfoVo
import com.tianli.dispatch.vo.GetGameTokenRspVo
import com.tianli.dispatch.vo.LoginRspVo
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
    fun passwordLogin(@RequestBody passwordLoginReq: PasswordLoginReqDto): Mono<SdkR<LoginRspVo?>> {
        //TODO: ban blacklist ip, error code=-206
        //check blank field
        if (StrUtil.isBlank(passwordLoginReq.account) || StrUtil.isBlank(passwordLoginReq.password))
            return Mono.just(SdkR.error(SdkRspCodeEnum.USERNAME_EMPTY))
        val account = passwordLoginReq.account?.let {
            passwordLoginReq.password?.let { it1 ->
            passwordLoginReq.is_crypto?.let { it2 -> sdkService.passwordLogin(it, it1, it2) }}}
            ?: return Mono.just(SdkR.error(SdkRspCodeEnum.ACCOUNT_OR_PASSWORD_ERROR))
        return login(account)
    }

    @PostMapping("/mdk/shield/api/verify")
    fun sessionKeyLogin(@RequestBody sessionKeyLoginReq: SessionKeyLoginReqDto): Mono<SdkR<LoginRspVo?>> {
        //TODO: ban blacklist ip, error code=-206
        //check blank field
        if (sessionKeyLoginReq.uid == null || StrUtil.isBlank(sessionKeyLoginReq.token))
            return Mono.just(SdkR.error(SdkRspCodeEnum.RELOGIN_REQUIRED))
        val account = sdkService.sessionKeyLogin(sessionKeyLoginReq.uid!!, sessionKeyLoginReq.token!!)
            ?: return Mono.just(SdkR.error(SdkRspCodeEnum.RELOGIN_REQUIRED))
        return login(account)
    }

    @PostMapping("/combo/granter/login/v2/login")
    fun requestGameToken(@RequestBody getGameTokenReq: GetGameTokenReqDto): Mono<SdkR<GetGameTokenRspVo?>> {
        //TODO: ban blacklist ip, error code=-206
        //check blank field
        val data = getGameTokenReq.data?: return Mono.just(SdkR.error(SdkRspCodeEnum.PARAMETER_ERROR))
        if (data.uid == null || StrUtil.isBlank(data.token))
            return Mono.just(SdkR.error(SdkRspCodeEnum.PARAMETER_ERROR))
        val account = sdkService.requestGameToken(data.uid!!, data.token!!)
            ?: return Mono.just(SdkR.error(SdkRspCodeEnum.RELOGIN_REQUIRED))
        val rsp = GetGameTokenRspVo(
            open_id = account.id,
            combo_token = account.gameToken
        )
        return Mono.just(SdkR.ok(rsp))
    }

    private fun login(account: Account): Mono<SdkR<LoginRspVo?>> {
        val accountInfo = AccountInfoVo(
            uid = account.id,
            name = account.email,
            email = account.email,
            token = account.sessionKey
        )
        val rsp = LoginRspVo(account = accountInfo)
        return Mono.just(SdkR.ok(rsp))
    }
}
