package com.tianli.dispatch.service.impl

import at.favre.lib.crypto.bcrypt.BCrypt
import com.tianli.dispatch.domain.Account
import com.tianli.dispatch.mapper.AccountMapper
import com.tianli.dispatch.service.SdkService
import org.springframework.stereotype.Service

@Service
class SdkServiceImpl(private val accountMapper: AccountMapper) : SdkService {
    override fun passwordLogin(userName: String, password: String, isCrypto: Boolean): Account? {
        val account = accountMapper.getAccountByUsername(userName) ?: return null
        //`is_crypto` field is ignored
        //verify password
        if (BCrypt.withDefaults().hashToString(12, password.toCharArray()) == account.password) {

        }

        return account

    }
}