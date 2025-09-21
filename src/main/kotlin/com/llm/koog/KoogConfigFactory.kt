package com.llm.koog

import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.clients.openrouter.OpenRouterModels
import com.llm.koog.configs.GeminiKoogConfig
import com.llm.koog.configs.KoogConfig
import com.llm.koog.configs.OpenRouterKoogConfig

object KoogClientFactory {
    fun fromEnv(): KoogConfig {
        return when (System.getenv("MODEL_PROVIDER")?.uppercase()) {
            "GEMINI" -> GeminiKoogConfig(
                apiKey = System.getenv("GEMINI_API_KEY"),
                modelName = GoogleModels.Gemini2_5Flash
            )

            "OPENROUTER" -> OpenRouterKoogConfig(
                apiKey = System.getenv("OPENROUTER_API_KEY"),
                model = when (System.getenv("OPENROUTER_MODEL")?.lowercase()) {
                    "gpt-oss-20b:free" -> CustomOpenRouterModels.GPT_OSS_20b_Free
                    "gpt-oss-120b" -> OpenRouterModels.GPT_OSS_120b
                    else -> error("Unsupported OpenRouter model")
                }
            )

            else -> error("Unsupported Koog provider")
        }
    }
}
