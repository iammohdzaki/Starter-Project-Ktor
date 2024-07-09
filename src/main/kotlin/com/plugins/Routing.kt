package com.plugins

import com.api.routes.ConfigRoutes
import com.api.routes.UserRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Server is Running!")
        }
        swaggerUI(path = "docs", swaggerFile = "openapi/documentation.yaml")
        UserRoutes()
        ConfigRoutes()
    }
}