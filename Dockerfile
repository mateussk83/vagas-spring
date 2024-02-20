FROM ubuntu:latest AS build

RUN apt-get update
RUN apiget install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

ROM openjdk:17-jdk-slim / EXPOSED 8080

COPY --from=build /target/vagas-spring-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]