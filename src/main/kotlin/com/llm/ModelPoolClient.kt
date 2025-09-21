package com.llm

import java.util.concurrent.atomic.AtomicInteger

class ModelPoolClient(
    private val clients: List<LLMClient>
) : LLMClient {

    private val counter = AtomicInteger(0)

    override suspend fun generateResponse(prompt: String): String {
        if (clients.isEmpty()) error("No LLM clients configured in pool")

        val index = (counter.getAndIncrement() % clients.size)
        val client = clients[index]

        return try {
            client.generateResponse(prompt)
        } catch (e: Exception) {
            // fallback: try others in pool
            val fallbackClients = clients.drop(index + 1) + clients.take(index)
            for (fallback in fallbackClients) {
                try {
                    return fallback.generateResponse(prompt)
                } catch (_: Exception) {
                    // continue
                }
            }
            throw e // all failed
        }
    }
}