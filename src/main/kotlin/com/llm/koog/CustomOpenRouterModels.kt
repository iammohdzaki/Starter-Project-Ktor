package com.llm.koog

import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel

object CustomOpenRouterModels {

    private val standardCapabilities: List<LLMCapability> = listOf(
        LLMCapability.Temperature,
        LLMCapability.Schema.JSON.Standard,
        LLMCapability.Speculation,
        LLMCapability.Tools,
        LLMCapability.ToolChoice,
        LLMCapability.Completion
    )

    val GPT_OSS_20b_Free: LLModel = LLModel(
        provider = LLMProvider.OpenRouter,
        id = "openai/gpt-oss-20b:free",
        capabilities = standardCapabilities,
        contextLength = 128_000
    )
}