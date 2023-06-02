package com.tianli.dispatch.mapper

import com.tianli.dispatch.domain.Account
import org.apache.ibatis.annotations.Select

interface AuthMapper {

    @Select("SELECT * FROM dispatch_user WHERE token = #{token}")
    fun getUserByToken(token:String): Account

    fun register(account: Account): Int

}
