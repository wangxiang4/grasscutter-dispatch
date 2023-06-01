package com.tianli.dispatch.props

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 *<p>
 * tianli config
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/6/1
 */
@Configuration
@EnableConfigurationProperties(ApplicationProperties::class)
@ConfigurationProperties(prefix = "tianli")
class TianliProperties {

    var language: String? = null

}
