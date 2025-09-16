FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /src

COPY pom.xml .
COPY src/main/resources/application.yml src/main/resources/application.yml
COPY src/main/resources/application-docker.yml src/main/resources/application-docker.yml

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

RUN addgroup -g 1001 -S fixly && \
    adduser -S -D -H -u 1001 -h /app -s /sbin/nologin -G fixly fixly

WORKDIR /app

RUN apk add --no-cache curl

COPY --from=build /src/target/*.jar app.jar

RUN chown fixly:fixly app.jar

USER fixly

EXPOSE 8000

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8000/actuator/health || exit 1

ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=docker", \
    "-Xmx512m", \
    "-Xms256m", \
    "-jar", \
    "app.jar"]