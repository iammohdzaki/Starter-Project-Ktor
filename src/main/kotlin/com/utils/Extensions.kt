package com.utils

import com.base.FailureResponse
import com.locale.Locale
import com.locale.Strings
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

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

fun normalizePath(path: String): String {
    // simple normalization: replace long numbers/uuids with placeholder
    // Adjust regexes for your route patterns (IDs, UUIDs, timestamps)
    var p = path
    // replace UUIDs
    p = p.replace(Regex("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"), "{id}")
    // replace numeric ids
    p = p.replace(Regex("/\\d+"), "/{id}")
    return p
}