# ============================
# BUILD STAGE
# ============================
FROM eclipse-temurin:21 AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw

# Build + Repackage to create executable jar
RUN ./mvnw clean package spring-boot:repackage -DskipTests


# ============================
# RUN STAGE
# ============================
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar   # executable JAR

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
