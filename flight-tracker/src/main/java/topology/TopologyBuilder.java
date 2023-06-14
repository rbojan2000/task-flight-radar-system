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
import utils.AirportKpiMapper;
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
                        .withTimestampExtractor(new TimestampExtractor())
        );

        GlobalKTable<String, AirportUpdateEvent> airportInputStream = builder.globalTable(
                properties.getProperty("kafka.topic.airport.update.events"),
                Consumed.with(Serde.stringSerde, Serde.specificSerde(AirportUpdateEvent.class, schemaRegistry)));


        // 1. Transformation of Flight update events
        TransformedFlightMapper transformedFlightMapper = new TransformedFlightMapper();

        KStream<String, TransformedFlight> transformedFlightStream = flightInputStream
                .mapValues(value -> transformedFlightMapper.transformFlightUpdateEventToTransformedFlight(value));

        transformedFlightStream
                .filter((key, value) -> !value.getStatus().toString().equals("CANCELED"))
//                .peek((key, value) -> System.out.println("transformedFlightStream key ".concat(key).concat(" value ").concat(value.toString())))
                .to(properties.getProperty("kafka.topic.radar.flights"), Produced.with(Serde.stringSerde, Serde.specificSerde(TransformedFlight.class, schemaRegistry)));


        // 2. Calculate Airport KPIs
        AirportKpiMapper airportKpiMapper = new AirportKpiMapper();

        KStream<String, TransformedFlight> transformedFlightStreamVithDepartureAirportCodeKEY = transformedFlightStream
                .map((key, value) -> KeyValue.pair(value.getDepartureAirportCode().toString(), value));


        //using starting destination as the point of view
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
                        (key, enrichedFlight, airportKpi) -> airportKpiMapper.updateAirportKpi(enrichedFlight, airportKpi),
                        Materialized.with(Serdes.String(), Serde.specificSerde(AirportKpi.class, schemaRegistry))

                );

        windowedAirportKpiKTable
                .toStream()
                .peek((key, value) -> System.out.println("windowedAirportKpiKTable key ".concat(key).concat(" value ").concat(value.toString()))).to(properties.getProperty("radar.airports.kpi"), Produced.with(Serde.stringSerde, Serde.specificSerde(AirportKpi.class, schemaRegistry)));

        return builder.build();
    }
}
