# ============================
# 📌 Stage 1 — Build the JAR
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy project files
COPY . .

# Build without tests (skip compile + execution)
RUN mvn -B clean package -Dmaven.test.skip=true


# ============================
# 📌 Stage 2 — Run the App
# ============================
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
