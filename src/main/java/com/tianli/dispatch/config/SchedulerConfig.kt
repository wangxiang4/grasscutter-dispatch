package com.tianli.dispatch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import java.util.concurrent.ThreadPoolExecutor


@Configuration
@EnableScheduling
class SchedulerConfig(private val environment:ConfigurableEnvironment){

    @Bean
    fun taskScheduler(): TaskScheduler {
        val name = environment.getProperty("spring.application.name")
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()
        threadPoolTaskScheduler.poolSize = 10
        threadPoolTaskScheduler.setThreadNamePrefix("$name-")
        threadPoolTaskScheduler.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        threadPoolTaskScheduler.initialize()
        return threadPoolTaskScheduler
    }

}
