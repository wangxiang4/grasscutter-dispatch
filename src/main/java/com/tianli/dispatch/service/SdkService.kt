package com.tianli.dispatch.service

import com.tianli.dispatch.domain.Account

interface SdkService {

    /**
     * Password login.
     * @return Account. If account not found or password incorrect, return null
     */
    fun passwordLogin(username: String, password: String, isCrypto: Boolean): Account?

}
