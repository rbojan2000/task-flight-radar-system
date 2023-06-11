import configuration.Configuration;
import model.Test;
import parser.TestParser;
import producer.TestProducer;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Runner {

    public static void main(String[] args) {
        try {
            start();
        } catch (IOException error) {
            System.exit(1);
        }
    }

    public static void start() throws IOException {

        Properties properties = Configuration.loadProperties("application.properties");

        TestProducer producer = new TestProducer();

        producer.setTopic(properties.getProperty("kafka.topic.user.actions"));

        producer.createKafkaProducer(properties.getProperty("kafka.server"), properties.getProperty("kafka.application.id"), properties.getProperty("kafka.producer.acks"), properties.getProperty("kafka.topic.compression.type"));

        List<Test> data = TestParser.parse(properties.getProperty("actions.input.resources"));

        producer.produce(data);
    }
}
