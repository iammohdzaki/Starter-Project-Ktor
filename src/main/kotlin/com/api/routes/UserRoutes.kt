package com.api.routes

import com.api.Endpoints
import com.api.controllers.UserController
import com.base.FailureResponse
import com.data.request.UserRequest
import com.utils.getBodyContent
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
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

    authenticate {
        get(Endpoints.USER_PROFILE_REQUEST) {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            if (!userId.isNullOrEmpty()) {
                val response = userController.getUserProfile(userId)
                call.respond(response)
            } else {
                call.respond(
                    FailureResponse<Any>(
                        statusCode = HttpStatusCode.BadRequest.value,
                        message = "Invalid Access Token!",
                    )
                )
            }
        }

        post(Endpoints.USER_PROFILE_EDIT_REQUEST) {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
        }
    }
}
