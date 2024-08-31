# Use a different official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the local project files to the container
COPY . /app

# Build the application
RUN ./mvnw clean install

# Specify the command to run your app
CMD ["java", "-jar", "target/Web_Scraper_-0.0.1-SNAPSHOT.jar"]
