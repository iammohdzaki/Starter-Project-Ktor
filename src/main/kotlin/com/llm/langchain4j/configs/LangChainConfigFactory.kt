package com.llm.langchain4j.configs

import com.utils.GeminiModel
import com.utils.OpenAiModel
import com.utils.OpenRouterModel

object LangChainConfigFactory {
    fun fromEnv(): LangChainConfig =
        when (System.getenv("MODEL_PROVIDER")?.uppercase()) {
            "GEMINI" ->
                GeminiConfig(
                    apiKey = System.getenv("GEMINI_API_KEY"),
                    model = GeminiModel.valueOf(System.getenv("GEMINI_MODEL") ?: "GEMINI_2_5_FLASH"),
                )

            "OPENAI" ->
                OpenAiConfig(
                    apiKey = System.getenv("OPENAI_API_KEY"),
                    model = OpenAiModel.valueOf(System.getenv("OPENAI_MODEL") ?: "GPT_3_5"),
                )

            "OPENROUTER" ->
                OpenRouterConfig(
                    apiKey = System.getenv("OPENROUTER_API_KEY"),
                    model = OpenRouterModel.valueOf(System.getenv("OPENROUTER_MODEL") ?: "openai/gpt-oss-20b:free"),
                )

            else -> error("Unsupported model provider")
        }
}
