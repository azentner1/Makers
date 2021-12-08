package com.the.markers.test_core


import com.apollographql.apollo.ApolloClient
import com.google.common.io.CharStreams
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

abstract class BaseTestClass {

    lateinit var apolloClient: ApolloClient

    @get:Rule
    var mockWebServer = MockWebServer()

    @Before
    fun init() {
        apolloClient = ApolloClient.builder()
                .serverUrl(mockWebServer.url("/"))
                .okHttpClient(OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS)
                        .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                        .build())
                .build()
    }

    @Throws(IOException::class)
    fun mockResponse(fileName: String): MockResponse {
        return MockResponse().setChunkedBody(readFileToString(javaClass, "/$fileName"), 32)
    }

    @After
    fun cleanUp() { 
        mockWebServer.close()
    }

    @Throws(IOException::class)
    fun readFileToString(contextClass: Class<*>,
                         streamIdentifier: String): String {

        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(contextClass.getResourceAsStream(streamIdentifier), Charset.defaultCharset())
            return CharStreams.toString(inputStreamReader)
        } catch (e: IOException) {
            throw IOException()
        } finally {
            inputStreamReader?.close()
        }
    }
}