FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle shadowJar --no-daemon

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*-all.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]