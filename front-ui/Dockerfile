FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/front-ui-1.0-SNAPSHOT.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]