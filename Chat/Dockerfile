FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/user/src
COPY pom.xml /home/user
RUN mvn -f /home/user/pom.xml clean package -DskipTests
EXPOSE 8080
EXPOSE 5432
ENTRYPOINT ["java", "-jar","/home/user/target/user_backend.jar"]
