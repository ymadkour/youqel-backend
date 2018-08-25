# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Make port 30002 available to the world outside this container
EXPOSE 30002

VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/youqel-0.0.1.jar

# Add the application's jar to the container
ADD ${JAR_FILE} youqel.jar

# Run the jar file 
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","/youqel.jar"]

