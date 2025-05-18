FROM openjdk:21-jdk-slim

WORKDIR /usr/src/app
COPY models models
COPY src src
COPY .mvn .mvn
COPY pom.xml mvnw ./

# Install
RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw
RUN ./mvnw -Dmaven.test.skip=true package

# Docker Run Command
EXPOSE 8082
CMD ["java","-jar","/usr/src/app/target/picturerecognition-0.0.1-SNAPSHOT.jar"]
