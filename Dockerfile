FROM openjdk:15-jdk-alpine3.12
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} scalator.jar
ENTRYPOINT ["java", "-jar", "scalator.jar"]