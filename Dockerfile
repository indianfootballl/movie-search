# Multi-stage build that does not rely on mvnw or .mvn in the repo

# 1) Build stage: use Maven with Java 17 to compile the backend
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy backend sources
COPY backend/pom.xml ./
COPY backend/src ./src

# Build the Spring Boot jar (skip tests for faster builds)
RUN mvn -q -DskipTests clean package

# 2) Runtime stage: use a slim JRE to run the jar
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy built jar from the build stage
COPY --from=build /app/target/movie-search-backend-1.0.0.jar /app/app.jar

# Expose the service port (Render sets PORT env var; Spring reads it via application.properties)
EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
