package com.llm

import com.llm.koog.KoogClientFactory
import com.llm.langchain4j.LangChain4jClient
import com.llm.langchain4j.configs.GeminiConfig
import com.llm.langchain4j.configs.LangChainConfigFactory
import com.llm.langchain4j.configs.OpenRouterConfig
import com.utils.GeminiModel
import com.utils.OpenRouterModel

object LLMClientFactory {
    fun fromEnv(): LLMClient {
        val poolEnv = System.getenv("LLM_POOL")?.takeIf { it.isNotBlank() }

        return if (poolEnv != null) {
            val clients = poolEnv.split(",").map { entry ->
                val parts = entry.split(":")
                val provider = parts[0].uppercase()
                val model = parts.getOrNull(1)

                when (provider) {
                    "GEMINI" -> {
                        val config = GeminiConfig(
                            apiKey = System.getenv("GEMINI_API_KEY") ?: error("Missing GEMINI_API_KEY"),
                            model = GeminiModel.valueOf(model ?: "GEMINI_2_5_FLASH")
                        )
                        LangChain4jClient(config.build())
                    }
                    "OPENROUTER" -> {
                        val config = OpenRouterConfig(
                            apiKey = System.getenv("OPENROUTER_API_KEY") ?: error("Missing OPENROUTER_API_KEY"),
                            model = OpenRouterModel.valueOf(model ?: "GPT_OSS_20B")
                        )
                        LangChain4jClient(config.build())
                    }
                    else -> error("Unsupported provider in LLM_POOL: $provider")
                }
            }
            ModelPoolClient(clients)
        } else {
            when (System.getenv("LLM_CLIENT")?.uppercase()) {
                "LANGCHAIN" -> {
                    val config = LangChainConfigFactory.fromEnv()
                    LangChain4jClient(config.build())  // returns ChatModel
                }

                "KOOG" -> {
                    val config = KoogClientFactory.fromEnv()
                    config.build()
                }

                else -> error("Unsupported LLM client provider: set LLM_CLIENT=LANGCHAIN or KOOG")
            }
        }
    }
}