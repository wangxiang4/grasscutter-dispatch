package com.tianli.dispatch.util

import cn.hutool.core.codec.Base64
import cn.hutool.core.io.resource.ClassPathResource
import cn.hutool.core.util.StrUtil
import com.tianli.dispatch.logger
import com.tianli.dispatch.vo.QueryCurRegionRspVo
import java.io.ByteArrayOutputStream
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import java.util.regex.Pattern
import javax.crypto.Cipher

class CryptoUtil {
    companion object {

        private val secureRandom = SecureRandom()

        var DISPATCH_KEY: ByteArray? = null

        var DISPATCH_SEED: ByteArray? = null

        private var CUR_AUTH_KEY: PrivateKey? = null

        private var CUR_SIGNING_KEY: PrivateKey? = null

        private var EncryptionKeys: MutableMap<Int, PublicKey> = HashMap()

        fun loadSecret() {
            DISPATCH_KEY = ClassPathResource("secret/dispatchKey.bin").readBytes()
            DISPATCH_SEED = ClassPathResource("secret/dispatchSeed.bin").readBytes()
            try {
                CUR_SIGNING_KEY = KeyFactory.getInstance("RSA")
                    .generatePrivate(PKCS8EncodedKeySpec(ClassPathResource("secret/SigningKey.der").readBytes()))

                CUR_AUTH_KEY = KeyFactory.getInstance("RSA")
                    .generatePrivate(PKCS8EncodedKeySpec(ClassPathResource("secret/auth_private-key.der").readBytes()))

                val pattern = Pattern.compile("([0-9]*)_Pub\\.der")
                for (path in FileUtil.getPathsFromResource("/secret/game")!!) {
                    if (path.toString().endsWith("_Pub.der")) {
                        val m = pattern.matcher(path.fileName.toString())
                        if (m.matches()) {
                            val key = KeyFactory.getInstance("RSA")
                                .generatePublic(X509EncodedKeySpec(FileUtil.read(path)))
                            EncryptionKeys[Integer.valueOf(m.group(1))] = key
                        }
                    }
                }
            } catch (e: Exception) {
                logger.error("An error occurred while loading keys.", e)
            }
        }

        fun xor(packet: ByteArray, key: ByteArray) {
            try {
                for (i in packet.indices) {
                    packet[i] = (packet[i].toInt() xor key[i % key.size].toInt()).toByte()
                }
            } catch (e: Exception) {
                logger.error("Crypto error.", e)
            }
        }


        @Throws(Exception::class)
        fun encryptAndSignRegionData(regionInfo: ByteArray, keyId: String?): QueryCurRegionRspVo? {
            if (StrUtil.isBlank(keyId)) {
                throw Exception("Key ID was not set")
            }
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, EncryptionKeys[Integer.valueOf(keyId)])

            //Encrypt regionInfo in chunks
            val encryptedRegionInfoStream = ByteArrayOutputStream()

            //Thank you so much GH Copilot
            val chunkSize = 256 - 11
            val regionInfoLength = regionInfo.size
            val numChunks = Math.ceil(regionInfoLength / chunkSize.toDouble()).toInt()
            for (i in 0 until numChunks) {
                val chunk = Arrays.copyOfRange(
                    regionInfo, i * chunkSize,
                    Math.min((i + 1) * chunkSize, regionInfoLength)
                )
                val encryptedChunk = cipher.doFinal(chunk)
                encryptedRegionInfoStream.write(encryptedChunk)
            }
            val privateSignature = Signature.getInstance("SHA256withRSA")
            privateSignature.initSign(CUR_SIGNING_KEY)
            privateSignature.update(regionInfo)
            val rsp = QueryCurRegionRspVo()
            rsp.content = Base64.encode(encryptedRegionInfoStream.toByteArray())
            rsp.sign = Base64.encode(privateSignature.sign())
            return rsp
        }

        fun decryptPassword(password: String): String {
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.DECRYPT_MODE, CUR_AUTH_KEY)
            return String(cipher.doFinal(Base64.decode(password)))
        }

        fun generateToken(length: Int = 32): String {
            val bytes = ByteArray(length)
            secureRandom.nextBytes(bytes)
            return BaseUtil.bytesToHex(bytes)
        }

    }
}
