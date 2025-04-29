# Step 1: Build
FROM gradle:7.6.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# Step 2: Run
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
