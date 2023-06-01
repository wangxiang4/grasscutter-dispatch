package com.tianli.dispatch.service

import com.tianli.dispatch.domain.User
import com.tianli.dispatch.vo.R
import org.springframework.web.server.WebSession

interface AuthService {

   /**
    * Get user based on token info
    * @param token
    * @return User
    */
   fun getUserByToken(token: String): User

   /**
    * Send mail captcha template
    * @param recipient mail recipient
    * @return void
    */
   fun sendMailCaptcha(recipient: String, webSession: WebSession): R<Boolean>

}
