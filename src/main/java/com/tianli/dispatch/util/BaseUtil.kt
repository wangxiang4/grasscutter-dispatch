package com.tianli.dispatch.util

import cn.hutool.core.lang.generator.SnowflakeGenerator

/**
 *<p>
 * Base Tools
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/5/30
 */
class BaseUtil {

    companion object {
        fun snowflakeId(): Long {
            val snowflakeGenerator = SnowflakeGenerator()
            return snowflakeGenerator.next()
        }
    }

}
