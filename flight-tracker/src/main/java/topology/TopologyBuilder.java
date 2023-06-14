package topology;

import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.streams.kstream.KStream;
import radar.AirportKpi;
import radar.AirportUpdateEvent;
import radar.FlightUpdateEvent;
import radar.TransformedFlight;
import serde.Serde;
import utils.TransformedFlightMapper;

@NoArgsConstructor
public class TopologyBuilder implements Serde {


    public static Topology buildTopology(Properties properties) {

        StreamsBuilder builder = new StreamsBuilder();
        String schemaRegistry = properties.getProperty("kafka.schema.registry.url");

        KStream<String, FlightUpdateEvent> flightInputStream = builder.stream(
                properties.getProperty("kafka.topic.flight.update.events"),
                Consumed.with(Serde.stringSerde, Serde.specificSerde(FlightUpdateEvent.class, schemaRegistry)));

        KStream<String, AirportUpdateEvent> airportInputStream = builder.stream(
                properties.getProperty("kafka.topic.airport.update.events"),
                Consumed.with(Serde.stringSerde, Serde.specificSerde(AirportUpdateEvent.class, schemaRegistry)));


        // 1. Transformation of Flight update events
        TransformedFlightMapper mapper = new TransformedFlightMapper();

        KStream<String, TransformedFlight> transformedFlightStream = flightInputStream
                .mapValues(value -> mapper.transformFlightUpdateEventToTransformedFlight(value));

        transformedFlightStream
                .peek((key, value) -> System.out.println(" Key: " + key + ", Value: " + value))
                .to(properties.getProperty("kafka.topic.radar.flights"), Produced.with(Serde.stringSerde, Serde.specificSerde(TransformedFlight.class, schemaRegistry)));


        // 2. Calculate Airport KPIs

        //counting departures for airports
        KTable<String, Long> numOfDeparturesPerAirport = transformedFlightStream
                .filter((key, value) -> !value.getStatus().toString().equals("SCHEDULED") && !value.getStatus().toString().equals("LANDED"))
                .map((key, value) -> KeyValue.pair(value.getDepartureAirportCode().toString(), value))
                .groupByKey(
                        Grouped.with(
                                Serdes.String(),
                                Serde.specificSerde(TransformedFlight.class, schemaRegistry))
                )
                .count();


        //counting canceled flights for airports
        KTable<String, Long> numOfcanceledFlightsPerAirport = transformedFlightStream
                .filter((key, value) -> value.getStatus().toString().equals("CANCELED"))
                .map((key, value) -> KeyValue.pair(value.getDepartureAirportCode().toString(), value))
                .groupByKey(
                        Grouped.with(
                                Serdes.String(),
                                Serde.specificSerde(TransformedFlight.class, schemaRegistry))
                )
                .count();


        //finding minimum flight duration per airport
        KTable<String, TransformedFlight> minFlightDurationPerDepartureAirport = transformedFlightStream
                .map((key, value) -> KeyValue.pair(value.getDepartureAirportCode().toString(), value))
                .groupByKey(
                        Grouped.with(
                                Serdes.String(),
                                Serde.specificSerde(TransformedFlight.class, schemaRegistry)
                        )
                )
                .reduce(
                        (value1, value2) -> value1.getDuration() < value2.getDuration() ? value1 : value2,

                        Materialized.with(
                                Serdes.String(),
                                Serde.specificSerde(TransformedFlight.class, schemaRegistry)
                        ));


        //finding latest departure timestamp of flight per airport
        KTable<String, TransformedFlight> latestDepartureTimestampPerDepartureAirport = transformedFlightStream
                .map((key, value) -> KeyValue.pair(value.getDepartureAirportCode().toString(), value))
                .groupByKey(
                        Grouped.with(
                                Serdes.String(),
                                Serde.specificSerde(TransformedFlight.class, schemaRegistry)
                        )
                )
                .reduce(
                        (value1, value2) ->value1.getDepartureTimestamp() > value2.getDepartureTimestamp() ? value1 : value2,
                        Materialized.with(
                                Serdes.String(),
                                Serde.specificSerde(TransformedFlight.class, schemaRegistry)
                        ));


        return builder.build();
    }
}
