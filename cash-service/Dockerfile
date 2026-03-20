FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/cash-service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]