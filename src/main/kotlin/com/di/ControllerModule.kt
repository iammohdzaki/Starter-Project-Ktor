package com.di

import com.api.controllers.ConfigController
import com.api.controllers.UserController
import org.koin.dsl.module

val controllerModule = module {

    single {
        UserController(get(), get(), get(), get(),get())
    }

    single {
        ConfigController(get())
    }
}
