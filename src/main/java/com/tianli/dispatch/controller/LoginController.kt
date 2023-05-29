package com.tianli.dispatch.controller;

import com.tianli.dispatch.domain.User
import com.tianli.dispatch.service.LoginService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
class LoginController(private val loginService: LoginService) {

    @GetMapping("/get/{id}")
    fun getUserById(@PathVariable id:Long):Mono<User>  {
        return Mono.just(loginService.getUserById(id))
    }
}
