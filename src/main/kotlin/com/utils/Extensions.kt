package com.utils

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.util.pipeline.PipelineContext

suspend inline fun <reified T : Any> PipelineContext<*, ApplicationCall>.getBodyContent(): T {
    return call.receive()
}
