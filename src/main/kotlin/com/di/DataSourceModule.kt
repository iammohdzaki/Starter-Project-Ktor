package com.di

import com.api.sources.ConfigDataSource
import com.api.sources.ConfigDataSourceImpl
import com.api.sources.UserDataSource
import com.api.sources.UserDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {

    single<UserDataSource> {
        UserDataSourceImpl(get())
    }

    single<ConfigDataSource> {
        ConfigDataSourceImpl(get())
    }
}