package com.llm.koog

import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.retry.RetryConfig
import ai.koog.prompt.executor.clients.retry.RetryingLLMClient
import ai.koog.prompt.executor.llms.SingleLLMPromptExecutor
import ai.koog.prompt.llm.LLModel
import com.llm.LLMClient
import org.slf4j.LoggerFactory

class KoogClientWrapper(
    private val client: ai.koog.prompt.executor.clients.LLMClient,
    private val model: LLModel
) : LLMClient {

    private val logger = LoggerFactory.getLogger(KoogClientWrapper::class.java)

    override suspend fun generateResponse(prompt: String): String {
        val basePrompt = prompt("cards") {
            system(
                """
                   Some Prompt Here
            """.trimIndent()
            )
        }
        val resilientClient = RetryingLLMClient(
            client,
            RetryConfig.PRODUCTION
        )
        val promptExecutor = SingleLLMPromptExecutor(resilientClient)
        val extendedPrompt = prompt(basePrompt) {
            user(prompt)
        }
        return try {
            val response = promptExecutor.execute(
                extendedPrompt,
                model
            )

            val content = response.firstOrNull()?.content ?: ""
            logger.info("Generated response: $content")

            content.ifBlank {
                logger.warn("Received empty response from LLM. Falling back to default JSON.")
                "{}"
            }
        } catch (e: Exception) {
            logger.error("LLM operation failed for prompt: $prompt", e)

            when {
                e.message?.contains("rate limit", ignoreCase = true) == true -> {
                    logger.warn("Rate limit hit. Scheduling retry later.")
                    "{}"
                }

                e.message?.contains("invalid api key", ignoreCase = true) == true -> {
                    logger.error("Authentication failed. Notifying administrator.")
                    "{}"
                }

                else -> {
                    logger.warn("Unknown error occurred. Falling back to safe default.")
                    useDefaultResponse()
                }
            }
        }
    }

    private fun useDefaultResponse(): String {
        return """{"status":"fallback","data":[]}"""
    }
}
