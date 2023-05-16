package com.tianli.auth.grasscutterauth.controller;

import com.tianli.auth.grasscutterauth.domain.User;
import com.tianli.auth.grasscutterauth.mapper.LoginMapper;
import com.tianli.auth.grasscutterauth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
class LoginController {

    private final LoginService loginService;


    @GetMapping("/*")
    // todo: config global
    public Mono<String> ssr(Model model) {
        String path = "auth/login/forgotpad";
        return Mono.create(monoSink -> monoSink.success(path));
    }

    @GetMapping("/get/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return Mono.just(loginService.getUserById(id));
    }

}
