FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/auth-service-1.0.0.jar auth-service-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "auth-service-1.0.0.jar"]