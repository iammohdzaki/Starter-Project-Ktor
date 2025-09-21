package com.utils

enum class ModelProvider {
    GEMINI,
    OPENAI,
    OPEN_ROUTER,
    MISTRAL,
}

enum class GeminiModel(
    val modelName: String,
) {
    GEMINI_1_5_PRO("gemini-1.5-pro"),
    GEMINI_2_5_FLASH("gemini-2.5-flash"),
}

enum class OpenAiModel(
    val modelName: String,
) {
    GPT_3_5("gpt-3.5-turbo"),
    GPT_4("gpt-4"),
    GPT_4O("gpt-4o"),
}

enum class OpenRouterModel(
    val modelName: String,
) {
    GPT_OSS_20B("openai/gpt-oss-20b:free"),
}