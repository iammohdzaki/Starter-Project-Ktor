package com.llm.langchain4j.configs

import com.utils.GeminiModel
import com.utils.ModelProvider
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel

data class GeminiConfig(
    val apiKey: String,
    val model: GeminiModel,
) : LangChainConfig {
    override val provider = ModelProvider.GEMINI
    override val modelName : String = model.modelName

    override fun build(): ChatModel =
        GoogleAiGeminiChatModel
            .builder()
            .apiKey(apiKey)
            .modelName(model.modelName)
            .logRequestsAndResponses(true)
            .build()
}