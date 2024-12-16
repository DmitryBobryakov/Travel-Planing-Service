FROM openjdk:17-jdk-slim
LABEL authors="HYPERPC"

WORKDIR /app
COPY target/STT_Project-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]