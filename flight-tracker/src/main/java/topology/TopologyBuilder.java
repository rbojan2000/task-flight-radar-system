package topology;

import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

import org.apache.kafka.streams.kstream.KStream;
import radar.*;
import serde.Serde;
import utils.TimestampExtractor;
import utils.TransformedFlightMapper;

@NoArgsConstructor
public class TopologyBuilder implements Serde {


    public static Topology buildTopology(Properties properties) {

        StreamsBuilder builder = new StreamsBuilder();
        String schemaRegistry = properties.getProperty("kafka.schema.registry.url");

        KStream<String, FlightUpdateEvent> flightInputStream = builder.stream(
                properties.getProperty("kafka.topic.flight.update.events"),
                Consumed.with(Serde.stringSerde, Serde.specificSerde(FlightUpdateEvent.class, schemaRegistry))
                        .withTimestampExtractor(new TimestampExtractor()));

        GlobalKTable<String, AirportUpdateEvent> airportInputStream = builder.globalTable(
                properties.getProperty("kafka.topic.airport.update.events"),
                Consumed.with(Serde.stringSerde, Serde.specificSerde(AirportUpdateEvent.class, schemaRegistry)));


        // 1. Transformation of Flight update events
        TransformedFlightMapper mapper = new TransformedFlightMapper();

        KStream<String, TransformedFlight> transformedFlightStream = flightInputStream
                .mapValues(value -> mapper.transformFlightUpdateEventToTransformedFlight(value));

        transformedFlightStream
                .peek((key, value) -> System.out.println("transformedFlightStream key " + key + " value " + value))
                .to(properties.getProperty("kafka.topic.radar.flights"), Produced.with(Serde.stringSerde, Serde.specificSerde(TransformedFlight.class, schemaRegistry)));


        // 2. Calculate Airport KPIs

        KStream<String, TransformedFlight> transformedFlightStreamVithDepartureAirportCodeKEY = transformedFlightStream
                .map((key, value) -> KeyValue.pair(value.getDepartureAirportCode().toString(), value));


        KStream<String, Flight> enrichedFlightStream = transformedFlightStreamVithDepartureAirportCodeKEY
                .leftJoin(
                        airportInputStream,
                        (flightKey, transformedFlight) -> transformedFlight.getDepartureAirportCode().toString(),
                        (transformedFlight, airportUpdate) -> new Flight(airportUpdate, transformedFlight)
                );


        KTable<String, AirportKpi> windowedAirportKpiKTable = enrichedFlightStream
                .groupByKey(
                        Grouped.with(
                                Serdes.String(),
                                Serde.specificSerde(Flight.class, schemaRegistry))
                )
                .aggregate(
                        AirportKpi::new,
                        (key, enrichedFlight, airportKpi) -> updateAirportKpi(enrichedFlight, airportKpi),
                        Materialized.with(Serdes.String(), Serde.specificSerde(AirportKpi.class, schemaRegistry))

                );

        windowedAirportKpiKTable
                .toStream()
                .peek((key, value) -> System.out.println("key " + key + "value " + value))
                .to(properties.getProperty("radar.airports.kpi"), Produced.with(Serde.stringSerde, Serde.specificSerde(AirportKpi.class, schemaRegistry)));

        return builder.build();
    }

    private static AirportKpi updateAirportKpi(Flight enrichedFlight, AirportKpi airportKpi) {
        airportKpi.setCode(enrichedFlight.getAirportUpdateEvent().getCode());
        airportKpi.setAirport(enrichedFlight.getAirportUpdateEvent().getAirport());
        airportKpi.setTz(enrichedFlight.getAirportUpdateEvent().getTz());
        airportKpi.setLongitude(enrichedFlight.getAirportUpdateEvent().getLongitude());
        airportKpi.setLatitude(enrichedFlight.getAirportUpdateEvent().getLatitude());
        airportKpi.setCountry(enrichedFlight.getAirportUpdateEvent().getCountry());
        airportKpi.setAirport(enrichedFlight.getAirportUpdateEvent().getAirport());
        airportKpi.setCity(enrichedFlight.getAirportUpdateEvent().getCity());

        if (!enrichedFlight.getTransformedFlight().getStatus().equals("SCHEDULED")
                && !enrichedFlight.getTransformedFlight().getStatus().equals("LANDED")) {

            Long departuresLast5Minutes = airportKpi.getDeparturesLast5Minutes();
            airportKpi.setDeparturesLast5Minutes(
                    departuresLast5Minutes != null ? departuresLast5Minutes + 1 : 1L
            );
        }

        if (enrichedFlight.getTransformedFlight().getStatus().equals("CANCELED")) {

            Long canceledFlightsLast5Minutes = airportKpi.getCanceledFlightsLast5Minutes();
            airportKpi.setCanceledFlightsLast5Minutes(
                    canceledFlightsLast5Minutes != null ? canceledFlightsLast5Minutes + 1 : 1L
            );
        }

        Long minFlightDuration = airportKpi.getMinFlightDuration();
        if (enrichedFlight.getTransformedFlight().getStatus().equals("LANDED") && (minFlightDuration == null || enrichedFlight.getTransformedFlight().getDuration() < minFlightDuration)) {
            airportKpi.setMinFlightDuration(Long.valueOf(enrichedFlight.getTransformedFlight().getDuration()));
        }

        Long lastDepartureTimestamp = airportKpi.getLastDepartureTimestamp();
        if (lastDepartureTimestamp == null || enrichedFlight.getTransformedFlight().getDepartureTimestamp() > lastDepartureTimestamp) {
            airportKpi.setLastDepartureTimestamp(enrichedFlight.getTransformedFlight().getDepartureTimestamp());
        }

        return airportKpi;
    }
}