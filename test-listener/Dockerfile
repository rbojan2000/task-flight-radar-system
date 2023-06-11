FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app/pom.xml
COPY src/main/resources/actions.csv /usr/app/src/main/resources/actions.csv
RUN mvn -f /usr/src/app/pom.xml clean install package

FROM openjdk:18-slim
COPY --from=build /usr/src/app/target/test-listener-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/test-listener.jar
WORKDIR /usr/app/
ENTRYPOINT ["java", "-jar", "/usr/app/test-listener.jar"]
