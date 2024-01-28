#
# Build & Package Stage
#
FROM maven AS build
WORKDIR /app
COPY .. .
RUN mvn -f ./pom.xml clean package

#
# Run stage
#
FROM openjdk
COPY --from=build /app/target/docker-0.0.1.jar /binaries/dockerLearning.jar
EXPOSE 8080
CMD ["java","-jar","/binaries/dockerLearning.jar"]
