package com.tianli.dispatch.controller

import com.tianli.dispatch.api.SdkR
import com.tianli.dispatch.dto.VerifyGameTokenReqDto
import com.tianli.dispatch.enums.SdkRspCodeEnum
import com.tianli.dispatch.mapper.AccountMapper
import com.tianli.dispatch.vo.VerifyGameTokenRspVo
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class ApiController(private val accountMapper: AccountMapper) {
    @PostMapping("/inner/account/verify/")
    fun verifyGameToken(@RequestBody verifyGameToken: VerifyGameTokenReqDto): Mono<SdkR<VerifyGameTokenRspVo?>> {
        val uid = verifyGameToken.open_id ?: return Mono.just(SdkR.error(SdkRspCodeEnum.PARAMETER_ERROR))
        val account = accountMapper.getAccountByUid(uid)
            ?: return Mono.just(SdkR.error(SdkRspCodeEnum.SDK_DATABASE_READ_FAIL))
        if (account.gameToken != verifyGameToken.combo_token)
            return Mono.just(SdkR.error(SdkRspCodeEnum.LOGIN_ABNORMAL))
        val rsp = VerifyGameTokenRspVo(
            account_uid = account.id
        )
        return Mono.just(SdkR.ok(rsp))
    }
}