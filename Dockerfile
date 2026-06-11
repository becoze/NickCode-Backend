# Docker image
FROM eclipse-temurin:8-jdk-alpine

# Copy local code to the container image.
WORKDIR /app

# Add file to the container image
COPY target/nickcode-back-0.0.1-SNAPSHOT.jar /app/nickcode-back-0.0.1-SNAPSHOT.jar

# Used port
EXPOSE 8103

# Run the web service on container startup.
ENTRYPOINT ["java","-jar","/app/nickcode-back-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]