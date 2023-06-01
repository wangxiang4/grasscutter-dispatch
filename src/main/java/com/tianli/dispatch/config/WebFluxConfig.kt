package com.tianli.dispatch.config

import com.tianli.dispatch.constant.AppConstants
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono


@Configuration
class WebFluxConfig {

    @Bean
    fun routerFunction(): RouterFunction<ServerResponse> {
        return router {
            (accept(MediaType.TEXT_HTML)
                    and "${AppConstants.API_DISPATCH}/auth"
            ).nest {
                GET("/*") { handleRequest(it) }
            }
        }
    }

    private fun handleRequest(request: ServerRequest): Mono<ServerResponse> {
        val model = mutableMapOf<String, Any>()
        model["name"] = "grasscutter-dispatch"
        model["version"] = "1.0.0"
        model["license"] = "MIT"
        model["description"] = "for grasscutter authorization dispatch"
        val path = request.path()
        val template = path.substring(1)
        return ServerResponse.ok().render(template, model)
    }

}
