package com.tianli.dispatch.util

import com.tianli.dispatch.logger
import org.springframework.beans.factory.DisposableBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationEvent
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

/**
 *<p>
 * Spring Tools
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/5/30
 */
@Service
@Lazy(false)
class SpringContextHolderUtil: ApplicationContextAware, DisposableBean {


    companion object {

        private var applicationContext: ApplicationContext? = null

        /** Get the application context stored in the static variable*/
        fun getApplicationContext(): ApplicationContext? {
            return applicationContext
        }

        /** Obtain the Bean from the static variable applicationContext, and automatically convert it to the type of the assigned object */
        fun <T> getBean(name: String): T? {
            return applicationContext?.getBean(name) as? T
        }

        /** Obtain the Bean from the static variable applicationContext, and automatically convert it to the type of the assigned object */
        fun <T> getBean(requiredType: Class<T>): T? {
            return applicationContext?.getBean(requiredType)
        }

        /** Clear the Application Context in the Spring Context Holder to Null. */
        fun clearHolder() {
            if (logger.isDebugEnabled) {
                logger.debug { "Clear the Application Context in the Spring Context Holder to Null:$applicationContext" }
            }
            applicationContext = null
        }

        /**
         * postEvent
         * @param event
         */
        fun publishEvent(event: ApplicationEvent?) {
            if (applicationContext == null) {
                return
            }
            applicationContext?.publishEvent(event!!)
        }


    }

    /** Implement the ApplicationContextAware interface and inject Context into static variables*/
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringContextHolderUtil.applicationContext = applicationContext
    }

    override fun destroy() {
        clearHolder()
    }

}
