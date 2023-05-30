package com.tianli.dispatch.factory

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import java.io.FileNotFoundException
import java.util.*

/**
 *<p>
 * Load A Custom Configuration File In Yml Format
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/5/30
 */
class YamlPropertySourceFactory: PropertySourceFactory {

    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
        val propertiesFromYaml = loadYamlIntoProperties(resource)
        val sourceName = (name ?: resource.resource.filename)!!
        return PropertiesPropertySource(sourceName, propertiesFromYaml!!)
    }

    @Throws(FileNotFoundException::class)
    private fun loadYamlIntoProperties(resource: EncodedResource): Properties? {
        return try {
            val factory = YamlPropertiesFactoryBean()
            factory.setResources(resource.resource)
            factory.afterPropertiesSet()
            factory.getObject()
        } catch (e: IllegalStateException) {
            val cause = e.cause
            if (cause is FileNotFoundException) {
                throw (e.cause as FileNotFoundException?)!!
            }
            throw e
        }
    }

}
