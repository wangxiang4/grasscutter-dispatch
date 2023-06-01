package com.tianli.dispatch.service

import com.tianli.dispatch.vo.R
import org.springframework.web.server.WebSession

interface AuthService {

   /**
    * send mail captcha template
    * @param recipient mail recipient
    * @return void
    */
   fun sendMailCaptcha(recipient: String, webSession: WebSession): R<Boolean>

}
