package com.tianli.dispatch.service.impl

import at.favre.lib.crypto.bcrypt.BCrypt
import cn.hutool.core.util.RandomUtil
import cn.hutool.core.util.StrUtil
import com.tianli.dispatch.constant.AppConstants
import com.tianli.dispatch.domain.Account
import com.tianli.dispatch.mapper.AccountMapper
import com.tianli.dispatch.props.DispatchProperties
import com.tianli.dispatch.service.AuthService
import freemarker.template.Configuration
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import org.springframework.web.server.WebSession
import java.io.StringWriter
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.util.*


@Service
class AuthServiceImpl(private val accountMapper: AccountMapper,
                      private val javaMailSender: JavaMailSender,
                      private val taskScheduler: TaskScheduler,
                      private val dispatchProperties: DispatchProperties,
                      private val environment: ConfigurableEnvironment
): AuthService {


    override fun getAccountByToken(token: String): Account? {
        return accountMapper.getAccountByGameToken(token)
    }

    override fun sendMailCaptcha(recipient: String, webSession: WebSession): Boolean {
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
        helper.setFrom(environment.getProperty("spring.mail.username")!!)
        helper.setTo(recipient)
        helper.setSubject("%s是你的原神验证码".format(code))
        helper.setText(stringWriter.toString(), true)
        javaMailSender.send(message)
        // handle storage session captcha
        webSession.attributes[recipient] = code
        val delay = Duration.ofSeconds(60)
        taskScheduler.schedule({ webSession.attributes.remove(recipient) }, Instant.now().plus(delay))
        return true
    }

    override fun register(account: Account): Account? {
        account.password = BCrypt.withDefaults().hashToString(12, account.password?.toCharArray())
        account.locale = StrUtil.emptyToDefault(dispatchProperties.language, Locale.getDefault().language)
        account.createTime = LocalDateTime.now()
        accountMapper.register(account)
        return account
    }

    override fun getAccountByUsername(username: String): Account? {
        return accountMapper.getAccountByUsername(username)
    }

    override fun resetPassword(account: Account, webSession: WebSession): Boolean {
        account.password = BCrypt.withDefaults().hashToString(12, account.password?.toCharArray())
        account.updateTime = LocalDateTime.now()
        val row = accountMapper.resetPassword(account)
        if (row == 0) return false
        webSession.attributes.remove(webSession.attributes["${account.email}-${AppConstants.FORGOTPAD_TOKEN_SUFFIX}"])
        return true
    }

    override fun selectMailBindAccount(email: String): List<Account> {
        return accountMapper.selectMailBindAccount(email)
    }
}
