package com.api.routing

import com.api.Endpoints
import com.api.configureServer
import com.api.controllers.ConfigController
import com.api.routes.ConfigRoutes
import com.api.sources.ConfigDataSource
import com.data.Configuration
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.assertEquals

class ConfigRoutesTest : KoinTest {

    private val configDataSource: ConfigDataSource = mockk()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single { ConfigController(configDataSource) }
                    single { configDataSource }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testGetConfigurations() = testApplication {
        configureServer()
        val config = Configuration("2e3r", true, false)

        coEvery { configDataSource.getConfiguration() } returns config

        routing {
            ConfigRoutes()
        }

        client.get(Endpoints.GET_CONFIG) {
            header("Accept-Language", "en")
        }.apply {
            println(bodyAsText())
            assertEquals(HttpStatusCode.OK, status)
        }

        coVerify { configDataSource.getConfiguration() }
    }

    @Test
    fun testUpdateConfiguration() = testApplication {
        configureServer()
        coEvery { configDataSource.updateConfiguration(any()) } returns true

        routing {
            ConfigRoutes()
        }

        client.post(Endpoints.UPDATE_CONFIG) {
            contentType(ContentType.Application.Json)
            setBody("{\"emailVerificationEnabled\": false,\"phoneVerificationEnabled\": true}")
            header("Accept-Language", "en")
        }.apply {
            println(bodyAsText())
            assertEquals(HttpStatusCode.OK, status)
        }

        coVerify { configDataSource.updateConfiguration(any()) }
    }
}