package com.tianli.dispatch.mapper

import com.tianli.dispatch.domain.Account

interface AccountMapper {

    fun getAccountByUsername(username: String): Account?

    fun getAccountByUid(uid: Int): Account?

    fun updateSessionKey(id: Int, sessionKey: String): Int

    fun updateGameToken(id: Int, gameToken: String): Int

    fun register(account: Account): Int

    fun resetPassword(account: Account): Int

    fun selectMailBindAccount(email: String): List<Account>

}
