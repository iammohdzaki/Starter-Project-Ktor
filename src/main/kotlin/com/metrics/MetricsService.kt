package com.metrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.DistributionSummary
import java.time.Duration
import java.util.concurrent.TimeUnit

object MetricsService {
    @Volatile
    private lateinit var registry: MeterRegistry

    fun init(reg: MeterRegistry) {
        registry = reg
    }

    // ----------------
    // HTTP request metrics
    // ----------------
    // Name: http.server.requests (Timer -> produces _seconds_* metrics for Prometheus)
    fun recordHttpRequest(uri: String, method: String, status: Int, durationMillis: Long) {
        // choose SLO buckets appropriate to your app (ms)
        val sloMillis = longArrayOf(50L, 100L, 250L, 500L, 1000L, 2500L, 5000L)
        val timer = Timer.builder("http.server.requests")
            .description("HTTP request latency")
            .tags("method", method, "uri", uri, "status", status.toString())
            .publishPercentileHistogram(true) // enable buckets for Prometheus histogram
            .serviceLevelObjectives(*sloMillis.map { Duration.ofMillis(it) }.toTypedArray())
            .register(registry)

        timer.record(durationMillis, TimeUnit.MILLISECONDS)

        // convenience counter for total requests
        val counter = Counter.builder("http.server.requests.count")
            .description("Total HTTP requests")
            .tags("method", method, "uri", uri, "status", status.toString())
            .register(registry)
        counter.increment()
    }

    // ----------------
    // LLM metrics
    // ----------------
    // LLM requests timer
    fun recordLLMRequest(
        provider: String,
        model: String,
        durationMillis: Long,
        promptTokens: Int,
        completionTokens: Int,
        success: Boolean
    ) {
        val timer = Timer.builder("llm.requests")
            .description("LLM call latency")
            .tags("provider", provider, "model", model)
            .publishPercentileHistogram(true)
            .serviceLevelObjectives(
                Duration.ofMillis(50),
                Duration.ofMillis(100),
                Duration.ofMillis(300),
                Duration.ofMillis(1000)
            )
            .register(registry)
        timer.record(durationMillis, TimeUnit.MILLISECONDS)

        // Counter for successes / failures
        val c = Counter.builder("llm.requests.count")
            .tags("provider", provider, "model", model, "status", if (success) "success" else "error")
            .register(registry)
        c.increment()

        // Tokens distribution (split by type)
        val promptSummary = DistributionSummary.builder("llm.tokens")
            .description("Tokens consumed per LLM call")
            .tags("provider", provider, "model", model, "type", "prompt")
            .serviceLevelObjectives(50.0, 200.0, 500.0, 1000.0)
            .register(registry)
        promptSummary.record(promptTokens.toDouble())

        val completionSummary = DistributionSummary.builder("llm.tokens")
            .description("Tokens consumed per LLM call")
            .tags("provider", provider, "model", model, "type", "completion")
            .serviceLevelObjectives(50.0, 200.0, 500.0, 1000.0)
            .register(registry)
        completionSummary.record(completionTokens.toDouble())
    }
}