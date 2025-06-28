# syntax=docker/dockerfile:1

# --- Build stage ---
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Accept build argument for Maven profile
ARG MAVEN_PROFILE=prod

# Disable caching for Maven dependencies and Flyway migrations
# Copy Maven wrapper and pom.xml
COPY pom.xml mvnw ./
COPY .mvn .mvn/
RUN chmod +x mvnw && ./mvnw dependency:go-offline --no-transfer-progress

# Copy the rest of the source code
COPY src ./src/

# Build the application with specified profile (skip tests for faster build)
# Disable caching for Flyway migrations by forcing a clean build
RUN ./mvnw clean package -DskipTests -P${MAVEN_PROFILE} --no-transfer-progress

# --- Runtime stage ---
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# Create a non-root user and group
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Set permissions
RUN chown -R appuser:appgroup /app
USER appuser

# Expose the default Spring Boot port
EXPOSE 8080

# JVM options: container-aware memory settings
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=80.0", "-jar", "/app/app.jar"]
