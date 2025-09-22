package com.llm.langchain4j.configs

import com.utils.ModelProvider
import com.utils.OpenAiModel
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.openai.OpenAiChatModel

data class OpenAiConfig(
    val apiKey: String,
    val model: OpenAiModel,
    val temperature: Double = 0.7,
    val maxTokens: Int = 1024,
) : LangChainConfig {
    override val provider = ModelProvider.OPENAI
    override val modelName : String = model.modelName

    override fun build(): ChatModel =
        OpenAiChatModel
            .builder()
            .apiKey(apiKey)
            .modelName(model.modelName)
            .temperature(temperature)
            .build()
}
