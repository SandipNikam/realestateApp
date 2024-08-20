FROM openjdk:17
COPY target/my-app-0.0.1-SNAPSHOT.jar my-app-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "my-app-0.0.1-SNAPSHOT.jar"]
