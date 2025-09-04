# ---- Stage 1: Build the application ----
FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /src

# Copy Maven configuration files first (for better layer caching)
COPY pom.xml .
COPY src/main/resources/application.yml src/main/resources/application.yml
COPY src/main/resources/application-docker.yml src/main/resources/application-docker.yml

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests -B

# ---- Stage 2: Run the application ----
FROM eclipse-temurin:21-jre-alpine

# Create a non-root user for security
RUN addgroup -g 1001 -S fixly && \
    adduser -S -D -H -u 1001 -h /app -s /sbin/nologin -G fixly fixly

WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

# Copy the jar from the build stage with a fixed name
COPY --from=build /src/target/*.jar app.jar

# Change ownership to the fixly user
RUN chown fixly:fixly app.jar

# Switch to non-root user
USER fixly

# Expose port 8080
EXPOSE 8080

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application with optimized JVM settings
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=docker", \
    "-Xmx512m", \
    "-Xms256m", \
    "-jar", \
    "app.jar"]