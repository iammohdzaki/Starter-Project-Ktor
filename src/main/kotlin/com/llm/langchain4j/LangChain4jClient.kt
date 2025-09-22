package com.llm.langchain4j

import com.llm.LLMClient
import com.llm.langchain4j.configs.LangChainConfig
import com.metrics.MetricsService
import dev.langchain4j.data.message.UserMessage.userMessage
import dev.langchain4j.kotlin.model.chat.chat
import dev.langchain4j.model.chat.ChatModel

class LangChain4jClient(private val chatModel: ChatModel, private val config: LangChainConfig) : LLMClient {
    override suspend fun generateResponse(prompt: String): String {
        val modelName = config.modelName
        val provider = config.provider.name
        val start = System.currentTimeMillis()
        return try {
            val response = chatModel.chat {
                messages += userMessage(prompt)
            }.aiMessage().text()

            val duration = System.currentTimeMillis() - start
            val tokens = response.length // optionally calculate proper token count
            MetricsService.recordLLMRequest(provider, modelName, duration, prompt.length,tokens, true)
            response
        } catch (ex: Exception) {
            val duration = System.currentTimeMillis() - start
            MetricsService.recordLLMRequest(provider, modelName, duration, prompt.length,0, false)
            throw ex
        }
    }
}