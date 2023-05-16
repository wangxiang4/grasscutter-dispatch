package com.tianli.auth.grasscutterauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/login")
class LoginController {

    @GetMapping("/*")
    public Mono<String> ssr(Model model) {
        String path = "auth/login/forgotpad";
        return Mono.create(monoSink -> monoSink.success(path));
    }

}
