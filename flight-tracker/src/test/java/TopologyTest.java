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

import java.io.IOException;
import java.util.Properties;

public class TopologyTest {

    private final Serdes.StringSerde stringSerde = Serde.stringSerde;
    private TestInputTopic<String, FlightUpdateEvent> flightUpdateEventTopic;
    private TestInputTopic<String, TransformedFlight> flightTopic;
    private SpecificAvroSerde specificAvroSerde;

    private TopologyTestDriver topologyTestDriver;
    private Properties properties;


    @Before
    public void setup() throws IOException {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        properties = new Properties(Configuration.loadProperties("application.properties"));

        this.specificAvroSerde = Serde.specificSerde(SpecificRecordBase.class, properties.getProperty("kafka.schema.registry.url"));

        var topology = TopologyBuilder.buildTopology(properties);

        topologyTestDriver = new TopologyTestDriver(topology, Configuration.getConfiguration(properties));

        createInputTopics(topologyTestDriver);
        createOutputTopics(topologyTestDriver);

    }

    public void createInputTopics(TopologyTestDriver driver) {

        this.flightUpdateEventTopic = driver.createInputTopic(
                properties.getProperty("kafka.topic.flight.update.events"),
                this.stringSerde.serializer(),
                this.specificAvroSerde.serializer()
        );


    }

    public void createOutputTopics(TopologyTestDriver driver) {

        this.flightTopic = topologyTestDriver.createInputTopic(
                properties.getProperty("kafka.topic.flight.update.events"),
                Serdes.String().serializer(), this.specificAvroSerde.serializer()
        );
    }

    @After
    public void tearDown() {
        // Close the test driver
        topologyTestDriver.close();
    }

    @Test
    public void shouldProduceFlightEvent() {
        flightUpdateEventTopic.pipeInput("id", new FlightUpdateEvent());


        //Assert.assertEquals("1", flightTopic.readKeyValue().key );
    }
}
