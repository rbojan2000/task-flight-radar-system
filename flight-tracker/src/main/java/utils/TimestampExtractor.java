package utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import radar.FlightUpdateEvent;

public class TimestampExtractor implements org.apache.kafka.streams.processor.TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var transformedFlight = (FlightUpdateEvent) record.value();
        return transformedFlight.getSTD();
    }
}
