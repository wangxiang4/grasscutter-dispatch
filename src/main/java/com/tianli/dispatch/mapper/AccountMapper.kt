package com.tianli.dispatch.mapper

import com.tianli.dispatch.domain.Account

interface AccountMapper {

    fun getAccountByUsername(username: String): Account?

    fun getAccountByGameToken(token: String): Account?

    fun register(account: Account): Int

}
