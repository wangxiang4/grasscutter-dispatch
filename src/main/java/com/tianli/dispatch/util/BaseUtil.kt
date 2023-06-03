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
        private val snowflakeGenerator = SnowflakeGenerator()
        fun snowflakeId(): Long {
            return snowflakeGenerator.next()
        }
        fun bytesToHex(bytes: ByteArray): String {
            return bytes.joinToString("") {
                String.format("%02x", it)
            }
        }
    }

}
