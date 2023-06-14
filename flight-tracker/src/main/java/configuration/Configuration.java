package configuration;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    public static Properties getConfiguration( Properties properties) {
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, properties.getProperty("kafka.application.id"));
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getProperty("kafka.bootstrap.server"));
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");

        return properties;
    }

    public static Properties loadProperties(String resourceFileName) throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        inputStream.close();

        return configuration;
    }
}
