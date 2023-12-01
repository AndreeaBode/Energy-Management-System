#FROM maven:3.8.3-openjdk-17 AS build
#COPY src /home/measure/src
#COPY pom.xml /home/measure
#RUN mvn -f /home/measure/pom.xml clean package -DskipTests
#EXPOSE 9001
#EXPOSE 5432
#ENTRYPOINT ["java", "-jar","/home/measure/target/spring-rabbitmq-consumer-0.0.1-SNAPSHOT.jar"]


FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]