package com.tianli.auth.grasscutterauth.controller;

import com.tianli.auth.grasscutterauth.domain.User
import com.tianli.auth.grasscutterauth.service.LoginService
import freemarker.template.Configuration
import lombok.RequiredArgsConstructor
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.io.StringWriter

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
class LoginController(private val loginService:LoginService) {

    @GetMapping("/get/{id}")
    fun getUserById(@PathVariable id:Long):Mono<User>  {
        return Mono.just(loginService.getUserById(id))
    }

}
