FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . /app/.
RUN ./gradlew bootWar
EXPOSE 8080
ENTRYPOINT ["./gradlew", "bootRun"]
