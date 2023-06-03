package com.tianli.dispatch.service.impl

import at.favre.lib.crypto.bcrypt.BCrypt
import com.tianli.dispatch.domain.Account
import com.tianli.dispatch.logger
import com.tianli.dispatch.mapper.AccountMapper
import com.tianli.dispatch.service.SdkService
import com.tianli.dispatch.util.CryptoUtil
import org.springframework.stereotype.Service

@Service
class SdkServiceImpl(private val accountMapper: AccountMapper) : SdkService {
    /**
     * Password login.
     * @return Account. If account not found or password incorrect, return null
     */
    override fun passwordLogin(userName: String, password: String, isCrypto: Boolean): Account? {
        val account = accountMapper.getAccountByUsername(userName) ?: return null
        //TODO: `is_crypto` field is ignored
        if (isCrypto) logger.warn("is_crypto is true. Unimplemented.")
        //verify password
        if (BCrypt.withDefaults().hashToString(12, password.toCharArray()) != account.password) return null
        //generate session key
        val sessionKey = CryptoUtil.generateSessionKey()
        account.sessionKey = sessionKey
        accountMapper.updateSessionKey(account.id!!, sessionKey)
        return account
    }

}