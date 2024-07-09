package com.api.routes

import com.api.Endpoints
import com.api.controllers.ConfigController
import com.utils.getBodyContent
import com.utils.language
import org.koin.ktor.ext.inject
import com.data.Configuration
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.ConfigRoutes() {

    val configController by inject<ConfigController>()

    get(Endpoints.GET_CONFIG) {
        val response = configController.getConfigurations(call.language())
        call.respond(response)
    }

    // Not Working Will Check Later
    post(Endpoints.UPDATE_CONFIG) {
        val requestBody = getBodyContent<Configuration>()
        val response = configController.updateConfiguration(requestBody, call.language())
        call.respond(response)
    }
}