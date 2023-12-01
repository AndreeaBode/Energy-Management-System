FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/ /app/resources/
ENTRYPOINT ["java", "-jar", "/app.jar"]