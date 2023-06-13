//import configuration.Configuration;
//import model.JsonSerde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.*;
//import org.apache.kafka.streams.test.TestRecord;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import topology.TestTopology;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Properties;
//
//public class TopologyTest {
//
//    private TestTopology topology;
//    private TestInputTopic<Long, model.Test> inputTopic;
//    private TestOutputTopic<Long, model.Test> outputTopic;
//    private TopologyTestDriver topologyTestDriver;
//
//    @Before
//    public void setup() throws IOException {
//        StreamsBuilder streamsBuilder = new StreamsBuilder();
//
//        JsonSerde<model.Test> testJsonSerde = new JsonSerde<>(model.Test.class);
//
//        Properties properties = new Properties(Configuration.loadProperties("application.properties"));
//
//        var topology = TestTopology.buildTopology(properties);
//
//        // Create and initialize the TopologyTestDriver
//        topologyTestDriver = new TopologyTestDriver(topology, properties);
//
//        // Create input and output topics for testing
//        inputTopic = topologyTestDriver.createInputTopic(
//                properties.getProperty("kafka.topic.user.actions"),
//                Serdes.Long().serializer(),
//                testJsonSerde.serializer()
//        );
//
//        outputTopic = topologyTestDriver.createOutputTopic(
//                properties.getProperty("kafka.topic.user.actions.output"),
//                Serdes.Long().deserializer(),
//                testJsonSerde.deserializer()
//        );
//    }
//
//    @After
//    public void tearDown() {
//        // Close the test driver
//        topologyTestDriver.close();
//    }
//
//    @Test
//    public void testTopology() {
//
//        model.Test t1 = new model.Test();
//        t1.setId(1L);
//        t1.setActivity("aktivnost");
//
//        model.Test t3 = new model.Test();
//        t3.setId(3L);
//        t3.setActivity("sdasdsa");
//
//
//        // Send test input records
//        inputTopic.pipeInput(1L, t1);
//        inputTopic.pipeInput(2L, t3);
//
//        // ... add more input records as needed
//
//        // Verify the expected output records
//        System.out.println("---------------");
//        outputTopic.readKeyValuesToList();
//        System.out.println("---------------");
//
//    }
//}
