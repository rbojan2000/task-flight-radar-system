package topology;

import lombok.NoArgsConstructor;
import model.JsonSerde;
import model.Test;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.sql.Time;
import java.time.Duration;
import java.util.Properties;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.common.serialization.Serdes;
@NoArgsConstructor
public class TestTopology {


    public static Topology buildTopology(Properties properties) {
        Serde<Test> userActionsSerdes = new JsonSerde<>(Test.class);

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<Long, Test> bankBalancesStream = streamsBuilder.stream(properties.getProperty("kafka.topic.user.actions"),
                Consumed.with(Serdes.Long(), userActionsSerdes))
                .filter((key, value) -> value.getActivity().equals("aktivnost"))
                .peek((key, value) -> System.out.println("1. Key: " + key + ", Value: " + value));


        bankBalancesStream.to(properties.getProperty("kafka.topic.user.actions.output"), Produced.with(Serdes.Long(), userActionsSerdes));




        // KOLIKO Se puta nasao userId u stream-u

//        KTable<String, Long> count = bankBalancesStream.groupBy(
//                        (key, value) -> value.getUserId(),
//                        Serialized.with(Serdes.String(), userActionsSerdes)
//                )
//                .count();
//
//        count.toStream()
//                .peek((key, value) -> System.out.println("------    1. Key: " + key + ", Value: " + value));

        //broji koliko se neki id nadje u prozoru

//        TimeWindows hoppingWindow = TimeWindows.of(Duration.ofMinutes(4)).advanceBy(Duration.ofMinutes(1));
//
//        KTable<Windowed<Long>, Long> count = bankBalancesStream.groupByKey().windowedBy(hoppingWindow)
//                .count();
//
//        count.toStream()
//                .peek((key, value) -> System.out.println("Window Key: " + key + ", Value: " + value));


        return streamsBuilder.build();
    }
}
