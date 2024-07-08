package com

import com.locale.Locale
import io.ktor.server.application.*
import com.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    Locale.init()
    configureKoin()
    setupConfig()
    configureCors()
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
