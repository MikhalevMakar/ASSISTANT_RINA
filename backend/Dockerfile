FROM openjdk:17-jdk-slim

WORKDIR /app/backend/.

COPY . /app/backend/.

RUN ./gradlew bootWar

EXPOSE 8080

ENTRYPOINT ["./gradlew", "bootRun"]
