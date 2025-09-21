# 🚀 Ktor Starter Project

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1-blue.svg)](https://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/Ktor-3.x-orange.svg)](https://ktor.io)
[![License](https://img.shields.io/github/license/iammohdzaki/Starter-Project-Ktor)](https://github.com/iammohdzaki/Starter-Project-Ktor/blob/main/LICENSE)

> A production-ready Ktor starter template with monitoring, LLM integration, and modern architecture patterns.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Architecture](#architecture)
- [LLM Integration](#llm-integration)
- [Monitoring & Metrics](#monitoring--metrics)
- [API Documentation](#api-documentation)
- [Deployment](#deployment)
- [Contributing](#contributing)

---

## 🎯 Overview

This starter project provides a robust foundation for building server-side applications with **Ktor**, designed for modern microservices and web applications. It combines essential production features like monitoring, database integration, and AI capabilities in a clean, maintainable architecture.

### 🌟 Key Highlights

| Feature | Description |
|---------|-------------|
| 📊 **Monitoring** | Prometheus + Micrometer with Grafana Cloud support |
| 🤖 **LLM Integration** | Pluggable AI clients (Langchain4j, Koog) |
| 🗄️ **Database** | MongoDB with local and Atlas support |
| 🔧 **DI Container** | Koin for clean dependency injection |
| 📚 **Auto Docs** | Swagger UI with live API documentation |
| 🔒 **Security** | JWT authentication and metrics protection |

---

## ✨ Features

### 🏗️ Core Framework
- **Ktor 3.x** with modular routing architecture
- **Kotlin 2.1** with coroutines support
- **Kotlin Serialization** for JSON handling

### 📊 Monitoring & Observability
- **Micrometer + Prometheus** metrics collection
- **JVM Metrics**: Memory, threads, garbage collection
- **System Metrics**: CPU load, uptime, disk usage
- **HTTP Metrics**: Request counts, latency, status codes
- **Grafana Cloud** integration ready
- **Basic Auth** protection for metrics endpoint

### 🤖 AI/LLM Integration
- **Multi-client support**: Langchain4j, Koog
- **Runtime switching** between LLM providers
- **Round-robin load balancing** across multiple models
- **Model provider abstraction** (OpenRouter, OpenAI, etc.)
- **Environment-based configuration**

### 🗄️ Database & Storage
- **MongoDB** integration (local + Atlas)
- **Connection pooling** and optimization
- **Environment-based configuration**

### 🛡️ Security & Auth
- **JWT** authentication
- **Basic Auth** for sensitive endpoints
- **Environment-based secrets management**

---

## 🚀 Quick Start

### Prerequisites

Before you begin, ensure you have:

- ☕ **JDK 11+** installed
- 📦 **Gradle 7+** or use included wrapper
- 🍃 **MongoDB** (local) or **Atlas** account
- 🐳 **Docker** (optional, for monitoring stack)

### 1️⃣ Clone & Setup

```bash
# Clone the repository
git clone https://github.com/iammohdzaki/Starter-Project-Ktor.git
cd Starter-Project-Ktor

# Make gradlew executable (Linux/Mac)
chmod +x gradlew
```

### 2️⃣ Environment Configuration

Create a `.env` file or set system environment variables:

```bash
# Database Configuration
MONGODB_CONNECTION_STRING="mongodb://localhost:27017"
MONGODB_DATABASE="ktor_database"

# Security
JWT_SECRET="your-super-secret-jwt-key-here"

# Monitoring (Optional)
METRICS_USER="admin"
METRICS_PASS="secure-password"

# LLM Configuration
LLM_CLIENT="LANGCHAIN"
MODEL_PROVIDER="OPENROUTER"
OPENROUTER_API_KEY="your-api-key"

# Single model
OPENROUTER_MODEL="GPT_OSS_20B"

# Or multiple models for load balancing
OPENROUTER_MODELS="anthropic/claude-3-haiku,openai/gpt-4o-mini,meta-llama/llama-3.1-8b"
```

### 3️⃣ Build & Run

```bash
# Build the project
./gradlew build

# Run the application
./gradlew run
```

🎉 **That's it!** Your application will be available at `http://localhost:8080`

---

## ⚙️ Configuration

### Development vs Production

| Environment | Config File | Usage |
|-------------|-------------|-------|
| **Development** | `application-dev.conf` | Local development with hot reload |
| **Production** | `application.conf` | Production deployment |

### Development Setup

For development with auto-reload:

```bash
# Add to your IDE's VM options or run configuration
-Dconfig.file=src/main/resources/application-dev.conf
```

### Configuration Files Structure

```hocon
# application.conf
ktor {
    deployment {
        port = 8080
        host = "0.0.0.0"
    }
}

database {
    connectionString = ${?MONGODB_CONNECTION_STRING}
    databaseName = ${?MONGODB_DATABASE}
}

jwt {
    secret = ${?JWT_SECRET}
    issuer = "ktor-app"
    audience = "ktor-audience"
}
```

---

## 🏗️ Architecture

### Project Structure

```
📁 Starter-Project-Ktor/
├── 📄 build.gradle.kts
├── 📁 src/main/kotlin/
│   ├── 📁 com/monitoring/          # Metrics & health checks
│   ├── 📁 com/llm/                 # LLM client wrapper
│   ├── 📁 di/                      # Dependency injection modules
│   ├── 📁 models/                  # Data models & DTOs
│   ├── 📁 routes/                  # API routes & controllers
│   └── 📄 Application.kt           # Application entry point
├── 📁 src/main/resources/
│   ├── 📁 languages/               # i18n localization files
│   ├── 📁 openapi/                 # OpenAPI/Swagger specs
│   ├── 📄 application.conf         # Production config
│   ├── 📄 application-dev.conf     # Development config
│   └── 📄 logback.xml             # Logging configuration
└── 📁 src/test/kotlin/             # Test files
```

### Dependency Injection with Koin

```kotlin
// Example DI module
val appModule = module {
    single<DatabaseService> { MongoDBService(get()) }
    single<LLMClient> { LLMClientFactory.fromEnv() }
    single<UserRepository> { UserRepositoryImpl(get()) }
}
```

---

## 🤖 LLM Integration

### Supported Clients & Providers

| Client | Providers | Models |
|--------|-----------|---------|
| **Langchain4j** | OpenAI, OpenRouter, Ollama | GPT-4, Claude, Llama |
| **Koog** | Custom providers | Configurable |

### Load Balancing Strategy

The LLM integration includes intelligent **round-robin load balancing** to distribute requests across multiple models, preventing overload and ensuring optimal performance:

- 🔄 **Automatic rotation** between configured models
- ⚖️ **Load distribution** to prevent API rate limits
- 🚦 **Failover protection** if a model becomes unavailable
- 📊 **Request tracking** per model for monitoring

### Usage Example

```kotlin
// Initialize LLM client from environment
val llmClient = LLMClientFactory.fromEnv()

// Generate response with automatic load balancing
val response = llmClient.generateResponse(
    prompt = "Explain quantum computing in simple terms",
    temperature = 0.7
)

println(response)
```

### Environment Configuration

#### Single Model Setup
```bash
# Switch between clients
LLM_CLIENT="LANGCHAIN"  # or "KOOG"

# Configure provider
MODEL_PROVIDER="OPENROUTER"
OPENROUTER_API_KEY="your-key"
OPENROUTER_MODEL="anthropic/claude-3-haiku"
```

#### Multi-Model Load Balancing
```bash
# Enable round-robin across multiple models
LLM_CLIENT="LANGCHAIN"
MODEL_PROVIDER="OPENROUTER"
OPENROUTER_API_KEY="your-key"

# Configure multiple models (comma-separated)
LLM_POOL=GEMINI:GEMINI_2_5_FLASH,OPENROUTER:GPT_OSS_20B

```

---

## 📊 Monitoring & Metrics

### Metrics Endpoint

The `/metrics` endpoint exposes comprehensive application metrics:

- **JVM Metrics**: Heap usage, garbage collection, thread counts
- **System Metrics**: CPU load, memory usage, uptime
- **HTTP Metrics**: Request rates, response times, status codes
- **Custom Metrics**: Business-specific measurements

### Security

Protect your metrics with Basic Authentication:

```bash
METRICS_USER="monitoring"
METRICS_PASS="super-secure-password"
```

### Grafana Cloud Integration

#### Step 1: Setup Data Source

1. Sign up for [Grafana Cloud Free](https://grafana.com/products/cloud/)
2. Add Prometheus data source:
   - **URL**: `https://your-app-domain.com/metrics`
   - **Auth**: Enable Basic Auth if configured
   - **Username/Password**: Your metrics credentials

#### Step 2: Import Dashboards

| Dashboard | ID | Description |
|-----------|----|-----------| 
| **JVM Micrometer** | `12900` | Complete JVM monitoring |
| **Ktor HTTP** | Custom | HTTP request metrics |

#### Step 3: Custom Panels

Create custom panels for:
- API response times by endpoint
- Error rates by HTTP status
- Database query performance
- LLM request latency

### Local Monitoring Stack

For local development and testing:

#### Prometheus Configuration

Create `prometheus.yml`:

```yaml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'ktor-app'
    metrics_path: '/metrics'
    static_configs:
      - targets: ['host.docker.internal:8080']
    basic_auth:
      username: 'admin'
      password: 'password'
    scrape_interval: 10s
```

#### Docker Compose Setup

```yaml
version: '3.8'
services:
  prometheus:
    image: prom/prometheus:latest
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'
      - '--storage.tsdb.path=/prometheus'
      - '--web.console.libraries=/etc/prometheus/console_libraries'

  grafana:
    image: grafana/grafana:latest
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - grafana-data:/var/lib/grafana

volumes:
  grafana-data:
```

Run with:

```bash
docker-compose up -d
```

Access:
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (admin/admin)

---

## 📚 API Documentation

### Swagger UI

Interactive API documentation is automatically generated and available at:

**🔗 http://localhost:8080/docs**

### Features

- **Live Testing**: Execute API calls directly from the browser
- **Schema Validation**: Request/response models with validation rules
- **Authentication**: JWT token testing support
- **Multiple Environments**: Switch between dev/staging/prod

### Custom Documentation

Add custom descriptions to your endpoints:

```kotlin
get("/users/{id}") {
    // OpenAPI documentation
    description = "Get user by ID"
    summary = "Retrieve user information"
    
    val userId = call.parameters["id"] ?: throw BadRequestException("Missing user ID")
    // ... endpoint logic
}
```

---

## 🚀 Deployment

### Environment Variables Checklist

Before deploying, ensure these variables are set:

#### Required
- [ ] `MONGODB_CONNECTION_STRING`
- [ ] `MONGODB_DATABASE`
- [ ] `JWT_SECRET`

#### Optional
- [ ] `METRICS_USER` / `METRICS_PASS`
- [ ] `LLM_CLIENT` / `MODEL_PROVIDER`
- [ ] `OPENROUTER_API_KEY` (if using LLM)

### Docker Deployment

```dockerfile
FROM openjdk:11-jre-slim

WORKDIR /app
COPY build/libs/*-all.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:

```bash
./gradlew buildFatJar
docker build -t ktor-app .
docker run -p 8080:8080 --env-file .env ktor-app
```

### Cloud Deployment

#### Railway
1. Connect GitHub repository
2. Set environment variables
3. Deploy automatically on push

#### Render
1. Create new web service
2. Connect repository
3. Set build command: `./gradlew buildFatJar`
4. Set start command: `java -jar build/libs/*-all.jar`

#### Heroku
```bash
heroku create your-app-name
heroku config:set MONGODB_CONNECTION_STRING="your-connection-string"
git push heroku main
```

---

## 🤝 Contributing

We welcome contributions! Here's how to get started:

### Development Setup

1. **Fork** the repository
2. **Clone** your fork locally
3. **Create** a feature branch:
   ```bash
   git checkout -b feature/amazing-feature
   ```
4. **Make** your changes
5. **Test** thoroughly
6. **Commit** with clear messages:
   ```bash
   git commit -m "feat: add amazing feature"
   ```
7. **Push** to your fork:
   ```bash
   git push origin feature/amazing-feature
   ```
8. **Create** a Pull Request

### Code Style

- Follow **Kotlin coding conventions**
- Use **meaningful variable names**
- Add **KDoc** comments for public APIs

### Areas for Contribution

- 🐛 **Bug fixes**
- ✨ **New features**
- 📚 **Documentation improvements**
- 🧪 **Test coverage**
- 🎨 **UI/UX enhancements**
- 🔒 **Security improvements**

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 🆘 Support

- 📖 **Documentation**: Check this README and inline code comments
- 🐛 **Issues**: Report bugs via [GitHub Issues](https://github.com/iammohdzaki/Starter-Project-Ktor/issues)
- 📧 **Contact**: mohdzaki407@gmail.com

---

<div align="center">

**⭐ Star this repository if it helped you!**

Made with ❤️ by [iammohdzaki](https://github.com/iammohdzaki)

</div>