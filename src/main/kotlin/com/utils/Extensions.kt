package com.utils

import com.base.FailureResponse
import com.locale.Locale
import com.locale.Strings
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import io.ktor.util.pipeline.PipelineContext

suspend inline fun <reified T : Any> RoutingContext.getBodyContent(): T = call.receive()

fun ApplicationCall.getLocaleString(key: String) =
    Locale.getString(key, this.request.headers["Accept-Language"] ?: "en")

fun ApplicationCall.language() =
    this.request.headers["Accept-Language"] ?: "en"

suspend fun RoutingContext.missingParameterReply(parameter: String) =
    call.respond(
        FailureResponse<Any>(
            statusCode = HttpStatusCode.BadRequest.value,
            message = "$parameter : ${call.getLocaleString(Strings.PARAMETER_MISSING)}",
        ),
    )

fun extractJsonBlock(raw: String): String =
    raw
        .replace("```json", "")
        .replace("```", "")
        .trim()
