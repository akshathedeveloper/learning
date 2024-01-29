FROM maven as build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk
COPY --from=build /app/target/docker-web-server-file-system-0.0.1.jar /binaries/web-server-file-system.jar
EXPOSE 8080
CMD ["java","-jar","/binaries/web-server-file-system.jar"]