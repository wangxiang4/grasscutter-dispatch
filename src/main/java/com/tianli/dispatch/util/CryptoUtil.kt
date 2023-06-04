package com.tianli.dispatch.util

import java.security.SecureRandom

class CryptoUtil {
    companion object {
        private val secureRandom = SecureRandom()
        fun generateToken(length: Int = 32): String {
            val bytes = ByteArray(length)
            secureRandom.nextBytes(bytes)
            return BaseUtil.bytesToHex(bytes)
        }
    }
}