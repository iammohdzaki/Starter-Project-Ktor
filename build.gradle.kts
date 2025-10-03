import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project
val kmongoVersion: String by project

plugins {
    application
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
    id("org.jlleitschuh.gradle.ktlint") version "13.0.0"
}

group = "com.starter.project"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    //Swagger
    implementation("io.ktor:ktor-server-swagger:$ktorVersion")

    //Generator
    implementation("com.github.iammohdzaki:Password-Generator:0.6")

    // Mongo Database
    implementation("org.litote.kmongo:kmongo:$kmongoVersion")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongoVersion")

    // Koin Core Features - DI
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    // SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    //Commons Codec - Password Hash
    implementation("commons-codec:commons-codec:1.15")

    //Core Server
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")

    //Serialization
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    //Server
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

    //Logger
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Langchain4j core
    implementation("dev.langchain4j:langchain4j:0.30.0")
    implementation("dev.langchain4j:langchain4j-kotlin:1.2.0-beta8")

    // LLM Models - LangChain4j
    implementation("dev.langchain4j:langchain4j-google-ai-gemini:1.2.0")
    implementation("dev.langchain4j:langchain4j-open-ai:1.2.0")

    // Koog
    implementation("ai.koog:koog-agents:0.5.0")

    // Monitoring
    implementation("io.ktor:ktor-server-metrics-micrometer:$ktorVersion") // Ktor plugin
    implementation("io.micrometer:micrometer-registry-prometheus:1.12.5") // Prometheus support
    implementation("io.micrometer:micrometer-core:1.12.5")

    //Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("io.insert-koin:koin-test:$koinVersion")
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    ktlint {
        android.set(false) // not an Android project
        ignoreFailures.set(false) // fail if issues found
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
        }
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}
