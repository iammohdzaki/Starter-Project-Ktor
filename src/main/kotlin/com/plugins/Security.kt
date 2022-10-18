package com.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.base.FailureResponse
import com.base.ServerConfig
import com.locale.Strings
import com.utils.getLocaleString
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
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
            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    FailureResponse<Any>(
                        statusCode = HttpStatusCode.Unauthorized.value,
                        message = call.getLocaleString(Strings.INVALID_TOKEN),
                    )
                )
            }
        }
    }

}
