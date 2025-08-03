
# Starter Project Ktor

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg)](https://kotlinlang.org)
[![Code Scanning](https://github.com/iammohdzaki/Starter-Project-Ktor/actions/workflows/code_scan.yml/badge.svg)](https://github.com/iammohdzaki/Starter-Project-Ktor/actions/workflows/code_scan.yml)
[![kotlin_lint](https://github.com/iammohdzaki/Starter-Project-Ktor/actions/workflows/kotlin_lint.yml/badge.svg)](https://github.com/iammohdzaki/Starter-Project-Ktor/actions/workflows/kotlin_lint.yml)
[![License](https://img.shields.io/github/license/iammohdzaki/Starter-Project-Ktor)](https://github.com/iammohdzaki/Starter-Project-Ktor/blob/main/LICENSE)

## Overview

This is a starter project for building server-side applications using [Ktor](https://ktor.io), a framework for building asynchronous servers and clients in connected systems. This template integrates MongoDB for database operations, Koin for dependency injection, and Swagger for API documentation. Serialization is handled using Kotlin Serialization.

## Features

- **Kotlin 2.0**: Leverage the latest features of Kotlin.
- **Ktor**: Asynchronous framework for building microservices and web applications.
- **MongoDB**: NoSQL database integration.
- **Koin**: Dependency injection framework.
- **Swagger**: Auto-generated API documentation.
- **Kotlin Serialization**: Serialize and deserialize Kotlin objects.

## Getting Started

### Prerequisites

- [JDK 11 or higher](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Gradle](https://gradle.org/install/)
- [MongoDB](https://www.mongodb.com/try/download/community)

### Installation

1. **Clone the repository**:

    ```bash
    git clone https://github.com/iammohdzaki/Starter-Project-Ktor.git
    cd Starter-Project-Ktor
    ```

2. **Build the project**:

    ```bash
    ./gradlew build
    ```

3. **Run the application**:

    ```bash
    ./gradlew run
    ```

### Configuration

The application can be configured using the `application.conf` file located in the `resources` directory.

### MongoDB Configuration

Ensure MongoDB is running locally or update the connection string in `application.conf`:

```hocon
mongodb {
    connectionString = "mongodb://localhost:27017"
    database = "ktor_database"
}
```

## API Documentation

Swagger is used for API documentation. Once the application is running, you can access the Swagger UI at `http://localhost:8080/docs`.

## Project Structure

```
Starter-Project-Ktor
├── build.gradle.kts
├── gradle
│   └── wrapper
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── src
    ├── main
    │   ├── kotlin
    │   │   ├── di
    │   │   ├── models
    │   │   ├── routes
    │   │   │   └── services
    │   ├── resources
    │   │   ├── languages
    │   │   │   ├── lang_ar.json
    │   │   │   └── lang_en.json
    │   │   ├── openapi
    │   │   │   └── documentation.yml
    │   │   ├── application.conf
    │   │   └── logback.xml
    └── test
        └── kotlin
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests.

1. Fork it (https://github.com/iammohdzaki/Starter-Project-Ktor/fork)
2. Create your feature branch (`git checkout -b feature/my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/my-new-feature`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
