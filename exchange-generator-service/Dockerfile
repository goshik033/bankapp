FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/exchange-generator-service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "app.jar"]