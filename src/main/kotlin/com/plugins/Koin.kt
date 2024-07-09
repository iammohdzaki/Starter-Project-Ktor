package com.plugins

import com.di.controllerModule
import com.di.dataSourceModule
import com.di.databaseModule
import com.di.mainModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        // Modules will be added here
        modules(
            mainModule,
            controllerModule,
            dataSourceModule,
            databaseModule
        )
    }
}
