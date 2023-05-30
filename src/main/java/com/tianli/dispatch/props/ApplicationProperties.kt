package com.tianli.dispatch.props

import com.tianli.dispatch.factory.YamlPropertySourceFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(factory = YamlPropertySourceFactory::class, value = ["classpath:application.yml"])
@EnableConfigurationProperties(ApplicationProperties::class)
@ConfigurationProperties(prefix = "spring.application")
class ApplicationProperties {

    var name: String? = null

}
