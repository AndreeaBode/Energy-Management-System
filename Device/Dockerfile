#FROM maven:3.8.3-openjdk-17 AS build
#COPY src /home/device/src
#COPY pom.xml /home/device
#RUN mvn -f /home/device/pom.xml clean package -DskipTests
#EXPOSE 8081
#EXPOSE 5432
#ENTRYPOINT ["java", "-jar","/home/device/target/device_backend.jar"]


FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]