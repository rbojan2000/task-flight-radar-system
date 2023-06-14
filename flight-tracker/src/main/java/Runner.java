import configuration.Configuration;
import org.apache.kafka.streams.KafkaStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import topology.TopologyBuilder;

import java.io.IOException;
import java.util.Properties;

public class Runner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties(Configuration.loadProperties("application.properties"));

        var configuration = Configuration.getConfiguration(properties);
        var topology = TopologyBuilder.buildTopology(properties);
        var kafkaStreams = new KafkaStreams(topology, configuration);

        LOGGER.info(topology.describe().toString());

        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }
}
