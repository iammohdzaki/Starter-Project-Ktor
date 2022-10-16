package com.api.routes

import com.api.Endpoints
import com.api.controllers.UserController
import com.data.request.UserRequest
import com.utils.getBodyContent
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.UserRoutes() {

    val userController by inject<UserController>()

    post(Endpoints.USER_REGISTER_REQUEST) {
        val requestBody = getBodyContent<UserRequest>()
        val response = userController.registerUser(requestBody)
        call.respond(response)
    }

    post(Endpoints.USER_LOGIN_REQUEST) {
        val requestBody = getBodyContent<UserRequest>()
        val response = userController.loginUser(requestBody)
        call.respond(response)
    }
}
