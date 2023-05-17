package com.tianli.auth.grasscutterauth.config

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


@Configuration
class OkHttpConfig {

    @Bean
    fun okHttpClient(): OkHttpClient {
        val sslContext = SSLContext.getInstance("TLS")
        val trustManager = createTrustManager()
        sslContext.init(null, arrayOf(trustManager), null)


        val connectionPool = ConnectionPool(
            200,
            500,
            TimeUnit.MILLISECONDS
        )

        return OkHttpClient.Builder()
            .connectionPool(connectionPool)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .sslSocketFactory(sslContext.socketFactory, trustManager as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer token")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    private fun createTrustManager(): TrustManager {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        val trustStore = loadTrustStore()
        trustManagerFactory.init(trustStore)
        val trustManagers = trustManagerFactory.trustManagers
        require(trustManagers.size == 1 && trustManagers[0] is X509TrustManager) {
            "Unexpected default trust managers: ${trustManagers.contentToString()}"
        }
        return trustManagers[0]
    }

    private fun loadTrustStore(): KeyStore {
        val trustStore = KeyStore.getInstance(KeyStore.getDefaultType())


        /*var pemFilePath = "/Users/wangxiang/IdeaProjects/my/grasscutter-auth/src/main/resources/ssl/auth_private-key.der"
        val pemInputStream: InputStream = FileInputStream(pemFilePath)
        CertificateFactory.getInstance("X.509").generateCertificate(pemInputStream)
        pemInputStream.close()


        val resource: InputStream = ClassPathResource("ssl/ca.pem").stream
        trustStore.load(null)
        CertificateFactory.getInstance("X.509").generateCertificate(resource)
        trustStore.setCertificateEntry("grasscutter-public", CertificateFactory.getInstance("X.509").generateCertificate(resource))*/
        return trustStore
    }
}
