package com.di

import com.llm.LLMClient
import com.llm.LLMClientFactory
import org.koin.dsl.module

val LLMModule =
    module {
        single<LLMClient> { LLMClientFactory.fromEnv() }
    }