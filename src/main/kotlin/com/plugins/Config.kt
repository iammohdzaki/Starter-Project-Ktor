package com.plugins

import com.base.ServerConfig
import com.base.jwt.TokenConfig
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.setupConfig() {
    val appConfig by inject<ServerConfig>()

    val config = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.issuer").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    appConfig.tokenConfig = config
}