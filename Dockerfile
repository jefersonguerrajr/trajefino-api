# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 1515
ENTRYPOINT ["java","-jar","app.jar"]
