package com.tianli.dispatch.service

import com.tianli.dispatch.domain.User

interface LoginService {

    fun getUserById(id: Long?): User

}
