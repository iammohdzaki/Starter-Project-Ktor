package com.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.base.ServerConfig
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val appConfig by inject<ServerConfig>()
    val config = appConfig.tokenConfig

    authentication {
        jwt {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(config.audience))
                    JWTPrincipal(credential.payload)
                else null
            }
        }
    }

}
