package com.utils

import com.locale.Locale
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.util.pipeline.PipelineContext

suspend inline fun <reified T : Any> PipelineContext<*, ApplicationCall>.getBodyContent(): T {
    return call.receive()
}

fun ApplicationCall.getLocaleString(key:String) =
    Locale.getString(key,this.request.headers["Accept-Language"] ?: "en")

fun ApplicationCall.language() =
    this.request.headers["Accept-Language"] ?: "en"