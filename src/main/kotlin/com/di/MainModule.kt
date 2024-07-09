package com.di

import com.base.ServerConfig
import com.base.hashing.HashingService
import com.base.hashing.HashingServiceImpl
import com.base.jwt.service.TokenService
import com.base.jwt.service.TokenServiceImpl
import org.koin.dsl.module

val mainModule = module {
    single<TokenService> {
        TokenServiceImpl()
    }

    single<HashingService> {
        HashingServiceImpl()
    }

    single {
        ServerConfig()
    }
}
