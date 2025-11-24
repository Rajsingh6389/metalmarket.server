# ============================
# Stage 1: Build Spring Boot App using Maven
# ============================
FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

# Copy Maven wrapper (if present)
COPY mvnw .
COPY .mvn .mvn

# Copy pom.xml first (dependency cache)
COPY pom.xml .

# Download dependencies (for cache efficiency)
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copy the rest of the source
COPY src src

# Build the JAR (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# ============================
# Stage 2: Run App (Lightweight Image)
# ============================
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copy JAR built in stage 1
COPY --from=builder /app/target/*.jar app.jar

# Render sets a random PORT environment variable
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]