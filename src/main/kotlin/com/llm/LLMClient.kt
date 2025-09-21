package com.llm

interface LLMClient {
    suspend fun generateResponse(prompt: String): String
}