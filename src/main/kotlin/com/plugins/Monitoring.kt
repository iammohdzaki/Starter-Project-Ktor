package com.plugins

import com.metrics.MetricsService
import com.utils.normalizePath
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.metrics.micrometer.MicrometerMetrics
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.logging.LogbackMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.binder.system.UptimeMetrics
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.slf4j.event.Level

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    val meterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    install(MicrometerMetrics) {
        registry = meterRegistry
        meterBinders = listOf(
            ClassLoaderMetrics(),
            JvmMemoryMetrics(),
            JvmGcMetrics(),
            JvmThreadMetrics(),
            ProcessorMetrics(),
            UptimeMetrics(),
            LogbackMetrics()
        )
    }

    // store registry globally for helper use
    MetricsService.init(meterRegistry)

    routing {
        authenticate("metricsAuth") {
            get("/metrics") {
                call.respondText(meterRegistry.scrape(), ContentType.Text.Plain)
            }
        }
    }

    intercept(ApplicationCallPipeline.Monitoring) {
        val start = System.nanoTime()
        try {
            proceed()
        } finally {
            val end = System.nanoTime()
            val durationMs = (end - start) / 1_000_000
            val rawPath = call.request.path()
            val normalized = normalizePath(rawPath)
            val method = call.request.httpMethod.value
            val status = call.response.status()?.value ?: 0
            MetricsService.recordHttpRequest(normalized, method, status, durationMs)
        }
    }
}
