package producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Test;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@NoArgsConstructor
@Getter
@Setter
public class TestProducer {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected String topic;
    protected String inputResourceName;
    protected KafkaProducer<Long, String> kafkaProducer;

    public void createKafkaProducer(String server, String clientID, String acks, String compresionType) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        props.put(ProducerConfig.CLIENT_ID_CONFIG, clientID);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compresionType);

        this.kafkaProducer = new KafkaProducer<>(props);
    }

    public void produce(List<Test> data) {
        data.stream()
            .map(userAction -> new ProducerRecord<>(topic, userAction.getId(), toJson(userAction)))
            .forEach(record -> {
                try {
                    send(kafkaProducer, record);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

    }

    private static void send(KafkaProducer<Long, String> producer, ProducerRecord<Long, String> record) throws ExecutionException, InterruptedException {
        producer.send(record).get();
    }

    private static String toJson(Test userAction) {
        try {
            return OBJECT_MAPPER.writeValueAsString(userAction);
        } catch (Exception e) {
            throw new RuntimeException("Error converting UserAction to JSON", e);
        }
    }
}
