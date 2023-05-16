package com.tianli.auth.grasscutterauth.service.impl

import com.tianli.auth.grasscutterauth.domain.User
import com.tianli.auth.grasscutterauth.mapper.LoginMapper
import com.tianli.auth.grasscutterauth.service.LoginService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class LoginServiceImpl(private val loginMapper: LoginMapper): LoginService {

    override fun getUserById(id: Long?): User? {
        return loginMapper.getUserById(id)
    }

}
