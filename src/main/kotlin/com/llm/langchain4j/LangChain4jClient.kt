package com.llm.langchain4j

import com.llm.LLMClient
import dev.langchain4j.data.message.UserMessage.userMessage
import dev.langchain4j.kotlin.model.chat.chat
import dev.langchain4j.model.chat.ChatModel

class LangChain4jClient(private val chatModel: ChatModel) : LLMClient {
    override suspend fun generateResponse(prompt: String): String {
        val response =
            chatModel.chat {
                messages += userMessage(prompt)
            }
        return response.aiMessage().text()
    }
}