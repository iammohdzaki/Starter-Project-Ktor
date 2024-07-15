package com.api

import com.plugins.configureSerialization
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder

fun ApplicationTestBuilder.configureServer() {
    application {
        configureSerialization()
    }
    environment {
        config = MapApplicationConfig(
            "jwt.issuer" to "http://0.0.0.0:8080",
            "jwt.audience" to "users",
            "jwt.realm" to "ktor starter app",
            "JWT_SECRET" to "TEST_SECRET_KEY"
        )
    }
}