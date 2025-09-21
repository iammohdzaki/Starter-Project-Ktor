package com.llm.koog.configs

import ai.koog.prompt.executor.clients.google.GoogleLLMClient
import ai.koog.prompt.llm.LLModel
import com.llm.koog.KoogClientWrapper

class GeminiKoogConfig(
    private val apiKey: String,
    private val modelName: LLModel
) : KoogConfig {
    override fun build(): KoogClientWrapper {
        val client = GoogleLLMClient(apiKey)
        return KoogClientWrapper(
            client,
            modelName
        )
    }
}