package com.llm.langchain4j.configs

import com.utils.ModelProvider
import dev.langchain4j.model.chat.ChatModel

interface LangChainConfig {
    val provider: ModelProvider

    fun build(): ChatModel
}
