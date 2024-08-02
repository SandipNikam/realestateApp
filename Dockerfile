# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/real-estate.jar .

# Make port 8080 available to the world outside this container
EXPOSE 8000

# Run the JAR file
ENTRYPOINT ["java", "-jar", "real-estate.jar"]
