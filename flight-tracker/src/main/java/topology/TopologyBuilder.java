package topology;

import lombok.NoArgsConstructor;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

import org.apache.kafka.streams.kstream.KStream;
import radar.AirportUpdateEvent;
import radar.FlightUpdateEvent;
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
//                .peek((key, value) -> System.out.println(" Key: " + key + ", Value: " + value));

        KStream<String, AirportUpdateEvent> airportInputStream = builder.stream(
                        properties.getProperty("kafka.topic.airport.update.events"),
                        Consumed.with(Serde.stringSerde, Serde.specificSerde(AirportUpdateEvent.class, schemaRegistry)));
  //              .peek((key, value) -> System.out.println(" Key: " + key + ", Value: " + value));



        //### 1. Transformation of Flight update events
        TransformedFlightMapper mapper = new TransformedFlightMapper();

        flightInputStream.mapValues(value -> mapper.transformFlightUpdateEventToTransformedFlight(value))
                .to(properties.getProperty("kafka.topic.radar.flights"));

        return builder.build();
    }
}
