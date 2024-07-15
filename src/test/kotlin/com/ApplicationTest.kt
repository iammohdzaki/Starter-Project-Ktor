package com

import com.api.routes.ServerRoutes
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        routing {
            ServerRoutes()
        }
        environment {
            config = MapApplicationConfig("JWT_SECRET" to "TEST_SECRET_KEY")
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Server is Running!", bodyAsText())
        }
    }
}