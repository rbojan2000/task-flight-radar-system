package streams.flightradar

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import radar.AirportUpdateEvent

case class AirportUpdateEventProducer(airports: Seq[AirportUpdateEvent])(implicit
    config: Configuration
) extends Thread
    with LazyLogging {

  val producer = new KafkaProducer[String, AirportUpdateEvent](config.streamProperties)

  override def run(): Unit =
    airports.foreach { airport =>
      producer.send(
        new ProducerRecord(config.flightRadar.topic.airportUpdateEvent, airport.code, airport)
      )
      logger.info(f"Published update airport event with code ${airport.code}")
    }
}
