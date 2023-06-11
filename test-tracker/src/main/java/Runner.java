import configuration.Configuration;
import model.Test;
import org.apache.kafka.streams.KafkaStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import topology.TestTopology;

import java.io.IOException;
import java.util.Properties;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties(Configuration.loadProperties("application.properties"));

        var configuration = Configuration.getConfiguration(properties);
        var topology = TestTopology.buildTopology(properties);
        var kafkaStreams = new KafkaStreams(topology, configuration);

        kafkaStreams.start();
        LOGGER.info("Topology: {}", topology.describe());

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }
}
