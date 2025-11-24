# ============================
# Stage 1: Build Spring Boot App
# ============================
FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle .
COPY src src

RUN chmod +x gradlew
RUN ./gradlew bootJar -x test

# ============================
# Stage 2: Run App (Lightweight Image)
# ============================
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Automatically copy the generated JAR without hardcoding the file name
COPY --from=builder /app/build/libs/*.jar app.jar

# Render assigns a random port → Spring Boot must read $PORT
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
