import configuration.Configuration;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import radar.FlightUpdateEvent;
import radar.TransformedFlight;
import serde.Serde;
import topology.TopologyBuilder;
import utils.TransformedFlightMapper;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TopologyTest {

    private final Serdes.StringSerde stringSerde = Serde.stringSerde;
    private TestInputTopic<String, FlightUpdateEvent> flightUpdateEventTestInputTopic;
    private TestOutputTopic<String, TransformedFlight> transformedFlightTestOutputTopic;
    private SpecificAvroSerde specificAvroSerde;

    private TopologyTestDriver topologyTestDriver;
    private Properties properties;


    @Before
    public void setup() throws IOException {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        properties = new Properties(Configuration.loadProperties("application.properties"));

        specificAvroSerde = Serde.specificSerde(SpecificRecordBase.class, properties.getProperty("kafka.schema.registry.url"));

        var topology = TopologyBuilder.buildTopology(properties);

        topologyTestDriver = new TopologyTestDriver(topology, Configuration.getConfiguration(properties));

        createInputTopics();
        createOutputTopics();

    }

    public void createInputTopics() {

        flightUpdateEventTestInputTopic = topologyTestDriver.createInputTopic(
                properties.getProperty("kafka.topic.flight.update.events"),
                stringSerde.serializer(),
                specificAvroSerde.serializer()
        );
    }

    public void createOutputTopics() {

        transformedFlightTestOutputTopic = topologyTestDriver.createOutputTopic(
                properties.getProperty("kafka.topic.radar.flights"),
                stringSerde.deserializer(), specificAvroSerde.deserializer()
        );

    }

    @After
    public void tearDown() {
        topologyTestDriver.close();
    }

    @Test
    public void shouldProduceFlightEventIntoTransformedFlightTestOutputTopic() {

        FlightUpdateEvent flightUpdateEvent = seedFlightEvent(
                "47876d11-77e8-3d84-8a72-46bf7a093aa2",
                "2023-04-05",
                "Albert/France(LFAQ)->Caransebes/Romania(LRCS)",
                1680714878089L,
                1680720998089L,
                "Europe/Paris->Europe/Bucharest",
                "SCHEDULED",
                "478",
                "47876d"
        );

        flightUpdateEventTestInputTopic.pipeInput(flightUpdateEvent.getId().toString(), flightUpdateEvent);
        KeyValue<String, TransformedFlight> keyValue = transformedFlightTestOutputTopic.readKeyValue();

        String key = keyValue.key;

        assertEquals(flightUpdateEvent.getId().toString(), key);
    }

    @Test
    public void shouldNOTProduceFlightEventIntoTransformedFlightTestOutputTopic() {

        FlightUpdateEvent flightUpdateEvent = seedFlightEvent(
                "47876d11-77e8-3d84-8a72-46bf7a093aa2",
                "2023-04-05",
                "Albert/France(LFAQ)->Caransebes/Romania(LRCS)",
                1680714878089L,
                1680720998089L,
                "Europe/Paris->Europe/Bucharest",
                "CANCELED",
                "478",
                "47876d"
        );
        flightUpdateEventTestInputTopic.pipeInput(flightUpdateEvent.getId().toString(), flightUpdateEvent);

        assertThrows(NoSuchElementException.class, () -> {
            transformedFlightTestOutputTopic.readKeyValue();
        });

    }

    @Test
    public void transformFlightUpdateEventToTransformedFlightTest() {
        String id = "47876d11-77e8-3d84-8a72-46bf7a093aa2";
        String date = "2023-04-05";
        String destination = "Albert/France(LFAQ)->Caransebes/Romania(LRCS)";
        Long STD = 1680714878089L;
        Long STA = 1680720998089L;
        String timezones = "Europe/Paris->Europe/Bucharest";
        String status = "SCHEDULED";
        String gate = "478";
        String airline = "47876d";

        FlightUpdateEvent flightUpdateEvent = seedFlightEvent(id, date, destination, STD, STA, timezones, status, gate, airline);

        TransformedFlight expectedTransformedFlight = new TransformedFlight();
        expectedTransformedFlight.setId(id);
        expectedTransformedFlight.setDate(date);
        expectedTransformedFlight.setFrom("Albert/France(LFAQ)");
        expectedTransformedFlight.setArrivalAirportCode("LRCS");
        expectedTransformedFlight.setDepartureAirportCode("LFAQ");
        expectedTransformedFlight.setDepartureTime("2023-04-05T17:14:38.089Z");
        expectedTransformedFlight.setArrivalTime("2023-04-05T18:56:38.089Z");
        expectedTransformedFlight.setDuration(102);
        expectedTransformedFlight.setStatus(status);
        expectedTransformedFlight.setGate(gate);
        expectedTransformedFlight.setAirline(airline);
        expectedTransformedFlight.setArrivalTimestamp(STA);
        expectedTransformedFlight.setDepartureTimestamp(STD);

        TransformedFlight transformedFlight = TransformedFlightMapper.transformFlightUpdateEventToTransformedFlight(flightUpdateEvent);

        assertEquals(expectedTransformedFlight, transformedFlight);
    }

    private FlightUpdateEvent seedFlightEvent(String id, String date, String destination, Long std, Long sta, String timezones, String status, String gate, String airline) {
        FlightUpdateEvent flightUpdateEvent = new FlightUpdateEvent();

        flightUpdateEvent.setId(id);
        flightUpdateEvent.setDate(date);
        flightUpdateEvent.setDestination(destination);
        flightUpdateEvent.setSTD(std);
        flightUpdateEvent.setSTA(sta);
        flightUpdateEvent.setTimezones(timezones);
        flightUpdateEvent.setStatus(status);
        flightUpdateEvent.setGate(gate);
        flightUpdateEvent.setAirline(airline);

        return flightUpdateEvent;
    }

}
