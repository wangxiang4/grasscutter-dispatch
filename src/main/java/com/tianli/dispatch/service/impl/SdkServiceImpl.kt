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


    override fun passwordLogin(username: String, password: String, isCrypto: Boolean): Account? {
        val account = accountMapper.getAccountByUsername(username) ?: return null
        //TODO: `is_crypto` field is ignored
        if (isCrypto) logger.warn("is_crypto is true. Unimplemented.")
        val decryptedPassword: String = if (isCrypto) {
            CryptoUtil.decryptPassword(password)
        } else {
            password
        }
        //verify password
        if (BCrypt.withDefaults().hashToString(12, decryptedPassword.toCharArray()) != account.password) return null
        //generate session key
        //TODO: get rid of generate duplicate session key
        val sessionKey = CryptoUtil.generateToken(24)
        account.sessionKey = sessionKey
        account.id?.let { accountMapper.updateSessionKey(it, sessionKey) }
        return account
    }

    override fun sessionKeyLogin(uid: Int, sessionKey: String): Account? {
        val account = accountMapper.getAccountByUid(uid) ?: return null
        if (account.sessionKey != sessionKey) return null
        return account
    }

    override fun requestGameToken(uid: Int, sessionKey: String): Account? {
        val account = sessionKeyLogin(uid, sessionKey) ?: return null
        //TODO: get rid of generate duplicate game token
        val gameToken = CryptoUtil.generateToken(20)
        account.gameToken = gameToken
        account.id?.let { accountMapper.updateGameToken(it, gameToken) }
        return account
    }
}
