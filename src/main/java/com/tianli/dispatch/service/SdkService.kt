package com.tianli.dispatch.service

import com.tianli.dispatch.domain.Account

interface SdkService {

    /**
     * Password login. If success, set session key and return account
     * @return Account. If account not found or password incorrect, return null
     */
    fun passwordLogin(username: String, password: String, isCrypto: Boolean): Account?
    /**
     * Session key login.
     * @return Account. If session key is invalid, return null
     */
    fun sessionKeyLogin(uid: Int, sessionKey: String): Account?
    /**
     * Request game token for the account.
     * @return Account with game token. If login failed, return null
     */
    fun requestGameToken(uid: Int, sessionKey: String): Account?
}
