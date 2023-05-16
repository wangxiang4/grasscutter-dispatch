package com.tianli.auth.grasscutterauth.service

import com.tianli.auth.grasscutterauth.domain.User

interface LoginService {

    fun getUserById(id: Long?): User?

}
