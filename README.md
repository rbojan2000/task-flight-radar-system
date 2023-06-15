## Table of contents
* [Flight Radar](#flight-radar)
* [Flight Tracker](#flight-tracker)


# Flight Radar

The Flight Radar task involves collecting and analyzing real-time Europe flight
data using a streaming platform.  The goal of this task is to create a system
that can process large amounts of flight data in real-time and provide useful
insights, such as flight status, delays, etc.  The system must be able to handle
streaming data, which requires a robust and scalable architecture.

## Getting Started

Besides chosen language and tools for stream-processing job, you will need
`docker` and `docker-compose`.

Initialize Kafka cluster & start flight producer with

```bash
docker-compose up -d
```

Provided `docker-compose.yml` will setup:

- Apache Kafka cluster with single broker
- Schema Registry with Avro support
- Required Kafka topics
- Event generator application

### System Entities

Entities are represented with [Avro](https://avro.apache.org/docs/) schema and
encoded with Avro specific binary protocol. Entities are registered within
Confluent schema registry.

Example of airport update record: [AirportUpdateEvent](flight-radar/src/main/avro/radar.AirportUpdateEvent.avsc).  
Example of flight update record: [FlightUpdateEvent](flight-radar/src/main/avro/radar.FlightUpdateEvent.avsc).

Flight update events are uniquely identified with `id`. Rest of the columns are shown in table below:

| Column        | Description                                                                           |
|---------------|---------------------------------------------------------------------------------------|
| `date`        | Date of flight in form `yyyy-mm-dd`.                                                  |
| `destination` | Start and end places in format `city/country(airportCode)->city/country(airportCode)` |
| `STD`         | Departure timestamp in UTC.                                                           |
| `STA`         | Arrival timestamp in UTC.                                                             |
| `timezones`   | Timezones of start and end place in format `start_tz->end_tz`                         |
| `status`      | `SCHEDULED / LANDED / LATE / CANCELED`                                                |
| `gate`        | Flight's gate identifier.                                                             |
| `airline`     | Name of the airline company.                                                          |

Airport update events are uniquely identified with Airport `code`. Rest of the
columns are shown in table below:

| Column      | Description                         |
|-------------|-------------------------------------|
| `airport`   | Name of the airport.                |
| `city`      | Airport location.                   |
| `country`   | Airport location.                   |
| `code`      | International airport ICAO code.    |
| `latitude`  | Latitude of city.                   |
| `longitude` | Longitude of city.                  |
| `tz`        | Timezone used in airport's location |

### Generator

Event generator application starts up with `docker-compose` and produces seeded
events to the following topics:

- `radar.airport.update.events`
  - key = `code: string`, value = [AirportUpdateEvent](flight-radar/src/main/avro/radar.AirportUpdateEvent.avsc)
- `radar.flight.update.events`
  - key = `id: string`, value = [FlightUpdateEvent](flight-radar/src/main/avro/radar.FlightUpdateEvent.avsc)

## Tasks

Task is to build a streaming application that reads input topics and performs
transformations described below.  The results of these tasks should be two
output topics:

- `radar.airports.kpi`
  - key = `airportCode: string`, value = `AirportKpi` Avro message
- `radar.flights`
  - key = `flightId: string`, value = `Flight` Avro message

### 1. Transformation of Flight update events

Flight update events read from topic `radar.flight.update.events` should be
transformed to follow Avro schema:

| Column                 | Description                                                                   |
|------------------------|-------------------------------------------------------------------------------|
| `id`                   | Id of the flight                                                              |
| `date`                 | Date of flight in form `yyyy-mm-dd`.                                          |
| `from`                 | Start place in format `city/country(airportCode)`                             |
| `departureAirportCode` | Code of the departure airport e.g. `BEG`                                      |
| `arrivalAirportCode`   | Code of the arrival airport e.g. `BEG`                                        |
| `departureTime`        | The date and time of the departure in ISO 8601 format: YYYY-MM-DDTHH:mm:ssTZD |
| `arrivalTime`          | The date and time of the arrival in ISO 8601 format: YYYY-MM-DDTHH:mm:ssTZD   |
| `departureTimestamp`   | Departure timestamp in UTC.                                                   |
| `arrivalTimestamp`     | Arrival timestamp in UTC.                                                     |
| `duration`             | Duration of the flight in minutes.                                            |
| `status`               | Status of the flight.                                                         |
| `gate`                 | Flight's gate identifier.                                                     |
| `airline`              | Name of the airline company.                                                  |

- Define Avro schema for Flight entity that is the results of transformations listed above.

Transformed flights should be published to the output topic `radar.flights`.
**Canceled flights should be ignored**

### 2. Calculate Airport KPIs

Using airport update events and transformed flight events calculate the
following KPIs using starting destination as the point of view.

| Column                        | Description                                                                                      |
|-------------------------------|--------------------------------------------------------------------------------------------------|
| `airport`                     | Name of the airport.                                                                             |
| `city`                        | Airport location.                                                                                |
| `country`                     | Airport location.                                                                                |
| `code`                        | International airport ICAO code.                                                                 |
| `latitude`                    | Latitude of city.                                                                                |
| `longitude`                   | Longitude of city.                                                                               |
| `departuresLast5Minutes`      | Number of the departures in last 5 minutes  **Exclude flights with statuses SCHEDULED and LANDED.** |
| `canceledFlightsLast5Minutes` | Number of the canceled flights in last 5 minutes  **Consider only flights with status CANCELED**   |
| `minFlightDuration`           | Minimal flight duration in minutes                                                               |
| `lastDepartureTimestamp`      | Latest departure timestamp in UTC.                                                               |

Events outside of 5 minute windows period should be ignored.  

### 3. Bonus

Reduce number of output messages on topic `radar.airports.kpi`. Wait for amount
of time (30 seconds) before emitting the aggregation result.

## Deliverables

- Working or even non-working code sent in zip archive or shared via git repository
- Code should be written in Java, Python or Scala
- Solution can include any stream processing engine but the following are preferred:
  - Apache Kafka Streams
  - Apache Spark Streaming
  - Apache Flink
  - Low-level Kafka Consumer / Producer API
- Code should contain README file that explains the approach and how to run the applications
- Deployment and cleanup should be as simple as possible

## General Advice

- Use common sense
- Keep things simple
- It’s much better to have a working solution than the perfect, but not working solution

## Evaluation Criteria

The top is the most important:

- Finished working sample (usable job with clear instructions how to run and use)
- Code quality
- Design quality (proper abstractions)
- Tests
- Performance
- Documented code (when it’s relevant)

Remember that simple is better than complex, and complex is better than
complicated.

Good luck & have fun!



# Flight Tracker

Flight Tracker is a solution based on stream processing using Apache Kafka Streams. It provides real-time tracking and processing of flight update events, along with the ability to calculate Airport KPIs (Key Performance Indicators).


## Topology
![topology](https://github.com/rbojan2000/task-flight-radar-system/assets/93132257/1dc0a1bc-f777-49ed-8510-fe3cb275ba71)

## Implemented tasks
 ### 1. Transformation of Flight update events:
 - Transformation of Flight update events: Flight update events are consumed from a Kafka topic and transformed into a standardized format (TransformedFlight) using the TransformedFlightMapper class. The transformed flight data is then sent to the "radar.flights" Kafka topic.

 ### 2. Calculate Airport KPIs
 - The transformed flight stream is enriched with airport information by joining it with a global table of airport update events. Using the starting destination as the point of view, airport KPIs are calculated based on the flight data within 5-minute time windows. The aggregated KPIs are stored in a windowed key-value store and then emitted to the "radar.airports.kpi" Kafka topic.
 - To enable windowing based on the departure timestamp (`STD`), a custom `TimestampExtractor` is used. The `TimestampExtractor` extracts the departure timestamp from the flight update event and assigns it as the event timestamp for windowing purposes.


### 3. Bonus
 - To reduce the number of output messages on the "radar.airports.kpi" topic, a delay of 30 seconds is introduced using the suppress operator. The aggregated KPIs are buffered and emitted only after the specified time delay.

## Scaling Application
 - To scale, you can run multiple instances of the application. Each instance will process a subset of Kafka partitions, providing parallel processing and increased throughput. 

 - Before starting each instance, make sure to update the `kafka.application.id` property in the `application.properties` file to a unique identifier. This ensures that each instance has a distinct application ID and avoids conflicts when multiple instances are running.

 - Since each Kafka topic --partitions 3, you can run up to 3 instances of the application for optimal scalability.


## Starting application
Flight-tracker application can be started via IDE or with command mvn compile exec:java -Dexec.mainClass=Runner.

