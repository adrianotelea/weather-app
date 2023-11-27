FROM gradle:8.0-jdk17-alpine as build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN gradle build -x test


FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs
COPY --from=build ${DEPENDENCY}/*.jar app.jar
COPY src/main/resources/data /src/main/resources/data
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app.jar"]