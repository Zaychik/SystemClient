FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} system-user-rest-0.0.1.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/system-user-rest-0.0.1.jar"]