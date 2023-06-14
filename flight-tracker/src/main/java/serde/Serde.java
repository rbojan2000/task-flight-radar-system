package serde;

import java.util.Map;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serdes;

public interface Serde<S, F extends SpecificRecordBase> {
    final Serdes.StringSerde stringSerde = new Serdes.StringSerde();

    static <T extends SpecificRecordBase> SpecificAvroSerde<T> specificSerde(Class<T> type, String schemaRegistryUrl) {
        SpecificAvroSerde<T> specificAvroSerde = new SpecificAvroSerde<T>();
        specificAvroSerde.configure(Map.of("schema.registry.url", schemaRegistryUrl), false);

        return specificAvroSerde;
    }
}
