package com.tianli.dispatch.service.impl

import com.tianli.dispatch.domain.User
import com.tianli.dispatch.mapper.LoginMapper
import com.tianli.dispatch.service.LoginService
import freemarker.template.Configuration
import lombok.RequiredArgsConstructor
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.StringWriter

@Service
@RequiredArgsConstructor
class LoginServiceImpl(private val loginMapper: LoginMapper,
                       private val javaMailSender: JavaMailSender
): LoginService {

    override fun getUserById(id: Long?): User {
        return loginMapper.getUserById(id)
    }

    private fun sendMailCaptcha(recipient: String){
        val configuration = Configuration(Configuration.VERSION_2_3_32)
        configuration.setClassForTemplateLoading(this::class.java, "/templates")
        val template = configuration.getTemplate("mail/captcha.ftlh")
        val stringWriter = StringWriter()
        val model = mutableMapOf<String, Any>()
        // todo: wait processing
        model["randomCaptcha"] = "102400"
        template.process(model, stringWriter)
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setFrom("no-reply@account.casks.me")
        helper.setTo(recipient)
        helper.setSubject("%s是你的原神验证码".format(model["randomCaptcha"]))
        helper.setText(stringWriter.toString(), true)
        javaMailSender.send(message)
    }

}
