FROM adoptopenjdk:8-jdk-hotspot

RUN apt-get update && apt-get install -y curl

# Install SBT
RUN curl -L -o sbt-1.3.5.deb https://repo.scala-sbt.org/scalasbt/debian/sbt-1.3.5.deb && \
    dpkg -i sbt-1.3.5.deb && \
    apt-get update && \
    apt-get install -y sbt && \
    rm sbt-1.3.5.deb

RUN apt-get install -y netcat

COPY . /app
WORKDIR /app
RUN sbt assembly

CMD ["java", "-jar", "app/target/scala-2.13/flight-radar-1.0.0.jar"]

