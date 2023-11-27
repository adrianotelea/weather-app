FROM gradle:8.0-jdk17-alpine as build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN gradle build -x test


FROM eclipse-temurin:17-jdk-alpine
ENV EXT_API_HOST=spring-boot-app-2
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs
COPY --from=build ${DEPENDENCY}/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]