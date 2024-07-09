package com

import com.locale.Locale
import com.plugins.configureCors
import com.plugins.configureKoin
import com.plugins.configureMonitoring
import com.plugins.configureRouting
import com.plugins.configureSecurity
import com.plugins.configureSerialization
import com.plugins.setupConfig
import io.ktor.server.application.Application

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
