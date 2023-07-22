FROM openjdk:11-jdk-slim

WORKDIR /app

COPY . /app/.

EXPOSE 8080

RUN ./mvn bootWar

ENTRYPOINT ["mvn", "spring-boot:run"]

