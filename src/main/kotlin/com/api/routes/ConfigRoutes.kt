package com.api.routes

import com.api.Endpoints
import com.api.controllers.ConfigController
import com.utils.getBodyContent
import com.utils.language
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import com.data.Configuration

fun Route.ConfigRoutes(){

    val configController by inject<ConfigController>()

    get(Endpoints.GET_CONFIG){
        val response = configController.getConfigurations(call.language())
        call.respond(response)
    }

    //Not Working Will Check Later
    post(Endpoints.UPDATE_CONFIG) {
        val requestBody = getBodyContent<Configuration>()
        val response = configController.updateConfiguration(requestBody,call.language())
        call.respond(response)
    }
}