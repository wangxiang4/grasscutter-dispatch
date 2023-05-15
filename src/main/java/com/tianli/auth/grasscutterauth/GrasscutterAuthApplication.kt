package com.tianli.auth.grasscutterauth;

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.tianli.auth.grasscutterauth.mapper")
class GrasscutterAuthApplication

fun main(args: Array<String>) {
    runApplication<GrasscutterAuthApplication>(*args)
}
