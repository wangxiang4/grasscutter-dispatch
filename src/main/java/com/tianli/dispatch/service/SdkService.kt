package com.tianli.dispatch.service

import com.tianli.dispatch.domain.Account

interface SdkService {
    fun passwordLogin(userName: String, password: String, isCrypto: Boolean): Account?
}