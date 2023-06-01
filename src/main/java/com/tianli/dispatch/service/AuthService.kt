package com.tianli.dispatch.service

import com.tianli.dispatch.domain.User
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
    * @return Boolean
    */
   fun sendMailCaptcha(recipient: String, webSession: WebSession): Boolean

   /**
    * register dispatch user
    * @param user
    * @return User
    */
   fun register(user:User): User

}
