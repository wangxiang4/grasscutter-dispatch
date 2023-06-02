package com.tianli.dispatch.controller;

import com.tianli.dispatch.constant.AppConstants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + AppConstants.SDK_NAME)
public class SdkController {
    @PostMapping("/mdk/shield/api/login")
    fun passwordLogin(@RequestParam username: String, @RequestParam password: String): Mono<String> {

    }
}
