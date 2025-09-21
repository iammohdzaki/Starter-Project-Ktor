package com.api.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.ServerRoutes() {
    get("/") {
        call.respondText("Server is Running!")
    }
}