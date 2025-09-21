package com.llm.koog.configs

import ai.koog.prompt.executor.clients.openrouter.OpenRouterLLMClient
import ai.koog.prompt.llm.LLModel
import com.llm.koog.CustomOpenRouterModels
import com.llm.koog.KoogClientWrapper

class OpenRouterKoogConfig(
    private val apiKey: String,
    private val model: LLModel = CustomOpenRouterModels.GPT_OSS_20b_Free
) : KoogConfig {
    override fun build(): KoogClientWrapper {
        val client = OpenRouterLLMClient(apiKey)
        return KoogClientWrapper(
            client,
            model
        )
    }
}