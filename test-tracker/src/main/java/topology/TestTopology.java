package topology;

import model.JsonSerde;
import model.Test;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

public class TestTopology {


    public static Topology buildTopology(Properties properties) {
        Serde<Test> userActionsSerdes = new JsonSerde<>(Test.class);

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<Long, Test> bankBalancesStream = streamsBuilder.stream(properties.getProperty("kafka.topic.user.actions"),
                Consumed.with(Serdes.Long(), userActionsSerdes))
                .peek((key, value) -> System.out.println("1. Key: " + key + ", Value: " + value));


        return streamsBuilder.build();
    }
}
