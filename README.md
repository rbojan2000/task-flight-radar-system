# Table of contents
* [Flight Radar](#flight-radar)
* [Flight Tracker](#flight-tracker)
  * [Topology](#topology)
  * [Implemented tasks](#implemented-tasks)
  * [Scalling application](#scaling-application)
  * [Starting application](#starting-application)
* [Flight Report](#flight-report)


# Flight Radar

The Flight Radar app involves collecting and analyzing real-time Europe flight
data using a streaming platform.  The goal of this task is to create a system
that can process large amounts of flight data in real-time and provide useful
insights, such as flight status, delays, etc.

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

- `radar.airport.update.events`
  - key = `code: string`, value = [AirportUpdateEvent](flight-radar/src/main/avro/radar.AirportUpdateEvent.avsc)
- `radar.flight.update.events`
  - key = `id: string`, value = [FlightUpdateEvent](flight-radar/src/main/avro/radar.FlightUpdateEvent.avsc)


### 1. Transformation of Flight update events

Flight update events read from topic `radar.flight.update.events`
transform flights to follow FlightUpdateEvent Avro schema:

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

Reduce number of output messages on topic `radar.airports.kpi`. Wait for amount
of time (30 seconds) before emitting the aggregation result.


# Flight Tracker

Flight Tracker is a solution based on stream processing using Apache Kafka Streams. It provides real-time tracking and processing of flight update events, along with the ability to calculate Airport KPIs (Key Performance Indicators).


# Flight Report

1. On-time Performance Analysis:
 - Calculate the percentage of flights that were on time (SCHEDULED) versus delayed (LATE) for each airline.
 - Identify airlines with the best and worst on-time performance.

2. Busiest Airports:
 - Determine the top 10 busiest airports based on the number of flights departing or arriving

3. Flight Duration Analysis:
 - Calculate the average duration of flights for each airline.
 - Identify airlines with the shortest and longest average flight durations.

4. Aerodrom Gate Utilization:
 - Determine the average number of flights per gate.
 - Identify gates that are frequently used and those that are underutilized.

5. Route Analysis:
 - Identify the most common flight routes based on start and end places.
 - Analyze the average delay time for each route.

6. Airline Comparison:
 - Compare the on-time performance of different airlines.
 - Create a ranking of airlines based on their overall performance.

7. Cancellation Analysis:
 - Calculate the percentage of canceled flights for each airline.

