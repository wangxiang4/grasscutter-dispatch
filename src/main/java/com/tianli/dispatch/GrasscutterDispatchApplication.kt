package com.tianli.dispatch;

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrasscutterDispatchApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<GrasscutterDispatchApplication>(*args)
        }
    }

}


