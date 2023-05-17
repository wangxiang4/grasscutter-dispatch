package com.tianli.auth.grasscutterauth.controller;

import com.tianli.auth.grasscutterauth.domain.User
import com.tianli.auth.grasscutterauth.service.LoginService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
class LoginController(private val loginService:LoginService) {

    @GetMapping("/get/{id}")
    fun getUserById(@PathVariable id:Long):Mono<User>  {
        return Mono.just(loginService.getUserById(id))
    }

}
