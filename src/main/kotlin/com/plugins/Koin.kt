package com.plugins
import com.di.controllerModule
import com.di.dataSourceModule
import com.di.mainModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin(){
    install(Koin){
        //Modules will be added here
        modules(
            mainModule,
            controllerModule,
            dataSourceModule
        )
    }
}
