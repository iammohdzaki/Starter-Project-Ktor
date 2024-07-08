package com.plugins

import com.api.routes.ConfigRoutes
import com.api.routes.UserRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*

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