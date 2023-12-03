FROM eclipse-temurin:17-jdk-alpine
WORKDIR /auth-service
COPY target/auth-service-1.0.0.jar auth-service-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "auth-service-1.0.0.jar"]