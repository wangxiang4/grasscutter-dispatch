package com.tianli.dispatch.util;

import com.tianli.dispatch.constant.SecurityConstants
import java.security.SecureRandom

/**
 *<p>
 * Security Encryption Util
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/6/3
 */
private val secureRandom = SecureRandom()
class SecurityUtil {

    companion object {

        fun bytesToHex(bytes: ByteArray?): String {
            if (bytes == null) return ""
            val hexChars = CharArray(bytes.size * 2)
            for (j in bytes.indices) {
                val v = bytes[j].toInt() and 0xFF
                hexChars[j * 2] = SecurityConstants.HEX_ARRAY[v ushr 4]
                hexChars[j * 2 + 1] = SecurityConstants.HEX_ARRAY[v and 0x0F]
            }
            return String(hexChars)
        }

        fun createSessionKey(length: Int): ByteArray? {
            val bytes = ByteArray(length)
            secureRandom.nextBytes(bytes)
            return bytes
        }

    }

}
