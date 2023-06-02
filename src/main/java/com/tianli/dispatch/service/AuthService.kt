package com.tianli.dispatch.service

import com.tianli.dispatch.domain.Account
import org.springframework.web.server.WebSession

interface AuthService {

   /**
    * Get user based on token info
    * @param token
    * @return User
    */
   fun getUserByToken(token: String): Account

   /**
    * Send mail captcha template
    * @param recipient mail recipient
    * @return Boolean
    */
   fun sendMailCaptcha(recipient: String, webSession: WebSession): Boolean

   /**
    * register dispatch user
    * @param account
    * @return User
    */
   fun register(account:Account): Account

}
