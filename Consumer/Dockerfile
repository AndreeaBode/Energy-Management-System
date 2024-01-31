FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/consumer/src
COPY pom.xml /home/consumer
RUN mvn -f /home/consumer/pom.xml clean package -DskipTests
EXPOSE 9001
EXPOSE 5432
ENTRYPOINT ["java", "-jar","/home/consumer/target/spring-rabbitmq-consumer-0.0.1-SNAPSHOT.jar"]


#FROM openjdk:17-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]