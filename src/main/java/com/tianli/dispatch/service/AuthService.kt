package com.tianli.dispatch.service

import com.tianli.dispatch.domain.Account
import org.springframework.web.server.WebSession

interface AuthService {

   /**
    * Get account based on token info
    * @param token
    * @return Account
    */
   fun getAccountByToken(token: String): Account?

   /**
    * Send mail captcha template
    * @param recipient mail recipient
    * @return Boolean
    */
   fun sendMailCaptcha(recipient: String, webSession: WebSession): Boolean

   /**
    * register dispatch account
    * @param account
    * @return Account
    */
   fun register(account:Account): Account?

   /**
    * Get account based on username info
    * @param username
    * @return Account
    */
   fun getAccountByUsername(username: String): Account?

   /**
    * Reset Bind Mail Account Password
    * @param account
    * @return Boolean
    */
   fun resetPassword(account: Account, webSession: WebSession): Boolean

   /**
    * Query Mail Bind Account
    * @param email
    * @return List<Account>
    */
   fun selectMailBindAccount(email: String): List<Account>

}
