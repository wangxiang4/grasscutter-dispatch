package com.tianli.auth.grasscutterauth;

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrasscutterAuthApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<GrasscutterAuthApplication>(*args)
        }
    }

}


