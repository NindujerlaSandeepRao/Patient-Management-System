# ----------- BUILD STAGE -----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy everything (IMPORTANT — includes resources like application.yml)
COPY . .

# Build the application
RUN mvn clean package -Dmaven.test.skip=true


# ----------- RUN STAGE -----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4004

ENTRYPOINT ["java", "-jar", "app.jar"]