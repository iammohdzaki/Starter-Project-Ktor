package com.llm.langchain4j.configs

import com.utils.ModelProvider
import com.utils.OpenRouterModel
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.openai.OpenAiChatModel

data class OpenRouterConfig(
    val apiKey: String,
    val model: OpenRouterModel,
) : LangChainConfig {
    override val provider = ModelProvider.OPEN_ROUTER

    override fun build(): ChatModel =
        OpenAiChatModel.builder()
            .apiKey(apiKey)
            .baseUrl("https://openrouter.ai/api/v1") // 👈 OpenRouter endpoint
            .modelName(model.modelName) // 👈 pick the model you want
            .temperature(0.7)
            .logRequests(true)
            .logResponses(true)
            .build()
}
