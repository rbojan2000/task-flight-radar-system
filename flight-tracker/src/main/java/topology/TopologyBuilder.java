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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import radar.*;
import serde.Serde;
import utils.AirportKpiMapper;
import utils.TimestampExtractor;
import utils.TransformedFlightMapper;
import org.apache.kafka.streams.kstream.Suppressed;


@NoArgsConstructor
public class TopologyBuilder implements Serde {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopologyBuilder.class);

    public static Topology buildTopology(Properties properties) {

        StreamsBuilder builder = new StreamsBuilder();
        String schemaRegistry = properties.getProperty("kafka.schema.registry.url");

        KStream<String, FlightUpdateEvent> flightInputStream = builder.stream(
                properties.getProperty("kafka.topic.flight.update.events"),
                Consumed.with(
                        Serde.stringSerde,
                        Serde.specificSerde(FlightUpdateEvent.class, schemaRegistry)
                ).withTimestampExtractor(new TimestampExtractor())
        );

        GlobalKTable<String, AirportUpdateEvent> airportInputStream = builder.globalTable(
                properties.getProperty("kafka.topic.airport.update.events"),
                Consumed.with(
                        Serde.stringSerde,
                        Serde.specificSerde(AirportUpdateEvent.class, schemaRegistry)
                )
        );


        // 1. Transformation of Flight update events

        KStream<String, TransformedFlight> transformedFlightStream = flightInputStream
                .mapValues(value -> TransformedFlightMapper.transformFlightUpdateEventToTransformedFlight(value));

        transformedFlightStream
                .filter((key, value) -> !value.getStatus().toString().equals("CANCELED"))
                .peek((key, value) -> LOGGER.info("transformedFlightStream key: ".concat(key).concat(" value: ").concat(value.toString())))
                .to(properties.getProperty("kafka.topic.radar.flights"), Produced.with(
                        Serde.stringSerde,
                        Serde.specificSerde(TransformedFlight.class, schemaRegistry)
                ));


        // 2. Calculate Airport KPIs

        KStream<String, TransformedFlight> transformedFlightStreamWithDepartureAirportCodeKEY = transformedFlightStream
                .map((key, value) -> KeyValue.pair(value.getDepartureAirportCode().toString(), value));


        // using starting destination as the point of view
        KStream<String, Flight> enrichedFlightStream = transformedFlightStreamWithDepartureAirportCodeKEY
                .leftJoin(
                        airportInputStream,
                        (flightKey, transformedFlight) -> transformedFlight.getDepartureAirportCode().toString(),
                        (transformedFlight, airportUpdate) -> new Flight(airportUpdate, transformedFlight)
                );


        Duration windowDuration = Duration.ofMinutes(5L);

        var windowedAirportKpiKTable = enrichedFlightStream
                .groupByKey(
                        Grouped.with(
                                Serdes.String(),
                                Serde.specificSerde(Flight.class, schemaRegistry)
                        )
                )
                .windowedBy(TimeWindows.of(windowDuration))
                .aggregate(
                        AirportKpi::new,
                        (key, enrichedFlight, airportKpi) -> AirportKpiMapper.updateAirportKpi(enrichedFlight, airportKpi),
                        Materialized.with(Serdes.String(), Serde.specificSerde(AirportKpi.class, schemaRegistry))
                );

        windowedAirportKpiKTable
                .suppress(Suppressed.untilTimeLimit(Duration.ofSeconds(30), Suppressed.BufferConfig.unbounded()))
                .toStream()
                .peek((key, value) -> LOGGER.info("windowedAirportKpiKTable key: ".concat(key.key()).concat(" value: ").concat(value.toString())))
                .map((key, value) -> KeyValue.pair(key.key(), value))
                .to(properties.getProperty("radar.airports.kpi"), Produced.with(
                        Serde.stringSerde,
                        Serde.specificSerde(AirportKpi.class, schemaRegistry)
                ));

        return builder.build();
    }
}
