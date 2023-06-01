package com.tianli.dispatch.service.impl

import cn.hutool.core.util.ObjectUtil
import cn.hutool.core.util.RandomUtil
import com.tianli.dispatch.mapper.AuthMapper
import com.tianli.dispatch.service.AuthService
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
class AuthServiceImpl(private val authMapper: AuthMapper,
                      private val javaMailSender: JavaMailSender,
                      private val taskScheduler: TaskScheduler
): AuthService {


    override fun sendMailCaptcha(recipient: String, webSession: WebSession): R<Boolean> {
        if (ObjectUtil.isEmpty(webSession.attributes[recipient])) {
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
            helper.setSubject("%s是你的原神验证码".format(code))
            helper.setText(stringWriter.toString(), true)
            javaMailSender.send(message)
            // handle storage session captcha
            webSession.attributes[recipient] = code
            val delay = Duration.ofSeconds(60)
            taskScheduler.schedule({ webSession.attributes.remove(recipient) }, Instant.now().plus(delay))
            return R.ok(true)
        }
        return R.error(false, "验证码已发送，请过一会儿再请求")
    }


}
