package com.tianli.dispatch.config;

import com.tianli.dispatch.util.CryptoUtil
import jakarta.annotation.PostConstruct
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener


@Configuration
class ApplicationConfig {

    @PostConstruct
    fun init() {
        CryptoUtil.loadSecret()
    }

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReady(event: ApplicationReadyEvent?) {
        // 在应用程序启动前执行的逻辑
    }

}
