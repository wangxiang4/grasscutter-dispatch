package com.tianli.dispatch.service.impl

import cn.hutool.core.util.RandomUtil
import com.tianli.dispatch.mapper.LoginMapper
import com.tianli.dispatch.service.LoginService
import com.tianli.dispatch.vo.R
import freemarker.template.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import org.springframework.web.server.WebSession
import java.io.StringWriter
import java.time.Duration
import java.time.Instant


@Service
class LoginServiceImpl(private val loginMapper: LoginMapper,
                       private val javaMailSender: JavaMailSender,
                       private val taskScheduler: TaskScheduler
): LoginService {


    override fun sendMailCaptcha(recipient: String, webSession: WebSession): R<Boolean> {
        val configuration = Configuration(Configuration.VERSION_2_3_32)
        configuration.setClassForTemplateLoading(this::class.java, "/templates")
        val template = configuration.getTemplate("mail/captcha.ftlh")
        val code = RandomUtil.randomNumbers(6)
        val stringWriter = StringWriter()
        val model = mutableMapOf<String, Any>()
        model["code"] = code
        template.process(model, stringWriter)
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setFrom("no-reply@account.casks.me")
        helper.setTo(recipient)
        helper.setSubject("%s是你的原神验证码".format(model["randomCaptcha"]))
        helper.setText(stringWriter.toString(), true)
        javaMailSender.send(message)
        // handle session captcha
        webSession.attributes[recipient] = code
        val delay = Duration.ofSeconds(60)
        taskScheduler.schedule({ webSession.attributes.remove(recipient) }, Instant.now().plus(delay))
        return R.ok(true)
    }



}
