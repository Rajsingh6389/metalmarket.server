# ============================
# 📌 Stage 1 — Build the JAR
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies first (caching)
COPY pom.xml .
RUN mvn -e -B dependency:go-offline

# Copy project source
COPY src ./src

# Build without tests
RUN mvn -e -B clean package -DskipTests


# ============================
# 📌 Stage 2 — Run the App
# ============================
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENV PORT=8080

ENTRYPOINT ["java", "-jar", "app.jar"]
