FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app/pom.xml
RUN mvn -f /usr/src/app/pom.xml clean install package

FROM openjdk:18-slim
COPY --from=build /usr/src/app/target/test-tracker-1.0.0-jar-with-dependencies.jar /usr/app/test-tracker-1.0.0-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar","/usr/app/test-tracker-1.0.0-jar-with-dependencies.jar"]